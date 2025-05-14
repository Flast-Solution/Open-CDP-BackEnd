package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.components.GetUserRole;
import vn.flast.controller.common.BaseController;
import vn.flast.entities.report.DataSaleFilter;
import vn.flast.entities.report.ReportActivity;
import vn.flast.models.User;
import vn.flast.resultset.ReportActivityLead;
import vn.flast.resultset.ReportActivityRevenue;
import vn.flast.resultset.ReportLeadSale;
import vn.flast.service.user.UserService;
import vn.flast.utils.DateUtils;
import vn.flast.utils.EntityQuery;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SaleReportService extends BaseController {

    private final DataService dataService;

    private final UserService userService;

    private final GetUserRole userRole;

    @PersistenceContext
    private EntityManager entityManager;

    public List<?> reportDataLeadSale(DataSaleFilter filter) {
        final String sql = """
                SELECT COUNT(DISTINCT d.id) as count, CASE d.status WHEN 0 THEN 'ChuaLienHe'
                WHEN 1 THEN 'KhongTrienKhai' WHEN 2 THEN 'DangTuVan' 
                WHEN 4 THEN 'KhongLienHeDuoc' WHEN 6 THEN 'LienHeSau'
                WHEN 7 THEN 'ThanhCohoi' ELSE 'UNKNOWN' END AS status,
                u.sso_id as sale FROM data d LEFT JOIN user u ON d.sale_id = u.id
                WHERE d.in_time BETWEEN :from AND :to AND d.sale_id IN (:saleId)
                GROUP BY d.status, u.sso_id;
                """;
        var lístSaleId = resolveSaleIds(filter);
        var nativeQuery = entityManager.createNativeQuery(sql, ReportLeadSale.REPORT_LEAD_SALE);
        nativeQuery.setParameter("from", filter.getFrom());
        nativeQuery.setParameter("to", filter.getTo());
        nativeQuery.setParameter("saleId", lístSaleId);
        List<ReportLeadSale> iList = EntityQuery.getListOfNativeQuery(nativeQuery, ReportLeadSale.class);
        return iList;
    }

    public List<?> reportActivityLead() {
        final String sql = """
        SELECT DATE(d.in_time) AS 'date',
               d.sale_id AS 'saleId',
               COUNT(d.id) AS 'total'
        FROM `data` d
        WHERE d.in_time BETWEEN :from AND :to
        GROUP BY DATE(d.in_time), d.sale_id
        ORDER BY d.sale_id;
        """;
        Date to = DateUtils.atEndOfDay(new Date());
        Date from = DateUtils.addDays(to, -15);

        var nativeQuery = entityManager.createNativeQuery(sql, ReportActivityLead.REPORT_ACTIVITY_LEAD);
        nativeQuery.setParameter("from", from);
        nativeQuery.setParameter("to", to);

        List<ReportActivityLead> data = EntityQuery.getListOfNativeQuery(nativeQuery, ReportActivityLead.class);

        Set<Integer> saleIds = data.stream().map(ReportActivityLead::getSaleId).collect(Collectors.toSet());
        LocalDate start = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Stream.iterate(start, d -> !d.isAfter(end), d -> d.plusDays(1))
                .map(date -> {
                    String dateStr = date.toString();
                    List<Map<String, Integer>> daily = saleIds.stream()
                            .map(saleId -> data.stream()
                                    .filter(e -> saleId.equals(e.getSaleId()) && date.equals(e.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
                                    .findFirst()
                                    .map(e -> Map.of("saleId", saleId, "total", e.getTotal()))
                                    .orElse(Map.of("saleId", saleId, "total", 0)))
                            .toList();
                    return Map.of("date", dateStr, "data", daily);
                })
                .toList();
    }

    public String getRoleUser() {
        var userName = getInfo();
        List<String> priority = List.of("ROLE_ADMIN", "ROLE_SALE_MANAGER", "ROLE_SALE");

        for (String role : priority) {
            boolean hasRole = userName.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals(role));
            if (hasRole) return role;
        }
        return "UNKNOWN";
    }

    public List<Integer> resolveSaleIds(DataSaleFilter filter) {
        String role = getRoleUser();
        List<Integer> userIds = new ArrayList<>();

        if (role.equals("ROLE_ADMIN")) {
            if ("all".equals(filter.getType()) || filter.getType() == null) {
                userIds = userService.findBySale().stream().map(User::getId).toList();
            } else if ("team".equals(filter.getType())) {
                userIds = userRole.listUserIdsByleader(filter.getLeaderId());
            } else {
                userIds.addAll(filter.getSaleId());
            }
        } else if (role.equals("ROLE_SALE_MANAGER")) {
            if ("team".equals(filter.getType()) || filter.getType() == null) {
                userIds = userRole.listUserIdsByleader(filter.getLeaderId());
            } else if ("personal".equals(filter.getType())) {
                userIds.add(getInfo().getId());
            } else {
                userIds.addAll(filter.getSaleId());
            }
        } else {
            userIds.add(getInfo().getId());
        }

        filter.setSaleId(userIds);
        return userIds;
    }


    public List<?> reportDataRevenue() {
        final String sql = """
        SELECT DATE(c.created_at) AS 'date',
               COUNT(CASE WHEN c.`type` = 'order' THEN c.id END) AS 'order',
               IFNULL(SUM(CASE WHEN c.`type` = 'order' THEN c.total END), 0) AS 'total',
               COUNT(CASE WHEN c.`type` = 'cohoi' THEN c.id END) AS 'cohoi'
        FROM `customer_order` c
        WHERE c.created_at BETWEEN :from AND :to
        GROUP BY DATE(c.created_at);
        """;

        Date to = DateUtils.atEndOfDay(new Date());
        Date from = DateUtils.addDays(to, -15);

        var nativeQuery = entityManager.createNativeQuery(sql, ReportActivityRevenue.REPORT_ACTIVITY_REVENUE);
        nativeQuery.setParameter("from", from);
        nativeQuery.setParameter("to", to);

        List<ReportActivityRevenue> data = EntityQuery.getListOfNativeQuery(nativeQuery, ReportActivityRevenue.class);

        Map<String, Map<String, Object>> dataMap = data.stream()
                .collect(Collectors.toMap(
                        e -> e.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString(),
                        e -> Map.of("order", e.getOrder(), "total", e.getTotal(), "cohoi", e.getCohoi())
                ));

        LocalDate start = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Stream.iterate(start, d -> !d.isAfter(end), d -> d.plusDays(1))
                .map(date -> {
                    String dateStr = date.toString();
                    Map<String, Object> entry = dataMap.getOrDefault(dateStr,
                            Map.of("order", 0, "total", 0, "cohoi", 0));
                    return Map.of("date", dateStr, "data", List.of(entry));
                })
                .toList();
    }

//    public List<?> reportActivitySale(){
//
//    }


    public ReportActivity newFeedReport(){
        ReportActivity reportActivity = new ReportActivity();
        reportActivity.setActivityLead(reportActivityLead());
        reportActivity.setActivityRevenue(reportDataRevenue());
//        reportActivity.setActivitySale();
        return reportActivity;
    }


}
