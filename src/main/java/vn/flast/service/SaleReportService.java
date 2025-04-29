package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.components.GetUserRole;
import vn.flast.controller.common.BaseController;
import vn.flast.entities.report.DataSaleFilter;
import vn.flast.models.User;
import vn.flast.resultset.ReportLeadSale;
import vn.flast.service.user.UserService;
import vn.flast.utils.EntityQuery;

import java.util.ArrayList;
import java.util.List;

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
        var role = getRoleUser();
        List<Integer> userIds = new ArrayList<>();
        if (role.equals("ROLE_ADMIN")) {
            if (filter.getType().equals("all") || filter.getType() == null) {
                userIds = userService.findBySale().stream().map(User::getId).toList();
                filter.setSaleId(userIds);
            } else if (filter.getType().equals("team")) {
                userIds = userRole.listUserIdsByleader(filter.getLeaderId());
                filter.setSaleId(userIds);
            }
        } else if (role.equals("ROLE_SALE_MANAGER")) {
            if(filter.getType().equals("team") || filter.getType() == null){
                userIds = userRole.listUserIdsByleader(filter.getLeaderId());
                filter.setSaleId(userIds);
            }else {
                userIds.add(getInfo().getId());
            }
        }else {
            userIds.add(getInfo().getId());
            filter.setSaleId(userIds);
        }
        var nativeQuery = entityManager.createNativeQuery(sql, ReportLeadSale.REPORT_LEAD_SALE);
        nativeQuery.setParameter("from", filter.getFrom());
        nativeQuery.setParameter("to", filter.getTo());
        nativeQuery.setParameter("saleId", filter.getSaleId());
        List<ReportLeadSale> iList = EntityQuery.getListOfNativeQuery(nativeQuery, ReportLeadSale.class);
        return iList;
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


}
