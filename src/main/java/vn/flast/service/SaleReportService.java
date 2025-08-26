package vn.flast.service;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.components.GetUserRole;
import vn.flast.controller.BaseController;
import vn.flast.entities.report.DataSaleFilter;
import vn.flast.models.User;
import vn.flast.records.ReportLeadSale;
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
        var nativeQuery = entityManager.createNativeQuery(sql, ReportLeadSale.REPORT_LEAD_SALE);
        nativeQuery.setParameter("from", filter.getFrom());
        nativeQuery.setParameter("to", filter.getTo());
        nativeQuery.setParameter("saleId", resolveSaleIds(filter));
        return EntityQuery.<ReportLeadSale>getListOfNativeQuery(nativeQuery);
    }

    public String getRoleUser() {
        var userName = getInfo();
        List<String> priority = List.of("ROLE_ADMIN", "ROLE_SALE_MANAGER", "ROLE_SALE");
        return priority.stream()
            .filter(role -> userName.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals(role)))
            .findFirst()
            .orElse("UNKNOWN");
    }

    public List<Integer> resolveSaleIds(DataSaleFilter filter) {
        String role = getRoleUser();
        List<Integer> userIds = new ArrayList<>();

        if (role.equals("ROLE_ADMIN")) {
            if ("all".equals(filter.getType()) || filter.getType() == null) {
                userIds = userService.findBySale().stream().map(User::getId).toList();
            } else if ("team".equals(filter.getType())) {
                userIds = userRole.listUserIdsByLeader(filter.getLeaderId());
            } else {
                userIds.addAll(filter.getSaleId());
            }
        } else if (role.equals("ROLE_SALE_MANAGER")) {
            if ("team".equals(filter.getType()) || filter.getType() == null) {
                userIds = userRole.listUserIdsByLeader(filter.getLeaderId());
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
}
