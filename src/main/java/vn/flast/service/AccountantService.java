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
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import vn.flast.domains.order.OrderService;
import vn.flast.entities.payment.PaymentFilter;
import vn.flast.models.CustomerOrder;
import vn.flast.pagination.Ipage;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.SqlBuilder;

@Service
@RequiredArgsConstructor
@Log4j2
public class AccountantService {

    @Lazy
    private final OrderService orderService;

    @PersistenceContext
    protected EntityManager entityManager;

    public Ipage<?> fetch(PaymentFilter filter) {

        int LIMIT = filter.getLimit();
        int PAGE = filter.page();
        int OFFSET = (filter.page()) * LIMIT;

        final String totalSQL = "FROM customer_order c left join customer_order_payment p on p.code = c.code";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addStringEquals("c.customer_mobile_phone", filter.getPhone());

        sqlBuilder.addIntegerEquals("p.is_confirm", 0);
        sqlBuilder.addStringEquals("p.code", filter.getCode());
        sqlBuilder.addDateBetween("p.in_time", filter.getFrom(), filter.getTo());
        sqlBuilder.addOrderByDesc("p.id");

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT c.* " + finalQuery, CustomerOrder.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var lists = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerOrder.class);
        var orders = orderService.transformDetails(lists);
        return  Ipage.generator(LIMIT, count, PAGE, orders);
    }
}
