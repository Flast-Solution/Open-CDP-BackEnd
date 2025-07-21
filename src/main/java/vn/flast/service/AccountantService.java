package vn.flast.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vn.flast.entities.payment.PaymentFilter;
import vn.flast.models.CustomerOrderPayment;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.CustomerOrderPaymentRepository;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.SqlBuilder;

@Service
@RequiredArgsConstructor
@Log4j2
public class AccountantService {

    private final CustomerOrderPaymentRepository customerOrderPaymentRepository;

    @PersistenceContext
    protected EntityManager entityManager;

    public Ipage<?> fetch(PaymentFilter filter){
        int LIMIT = filter.getLimit();
        int PAGE = filter.page();
        int OFFSET = (filter.page()) * LIMIT;
        final String totalSQL = "FROM `customer_order_payment` p left join customer_order c on p.code = c.code";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);
        sqlBuilder.addIntegerEquals("p.status", filter.getSaleId());
        sqlBuilder.addStringEquals("p.code", filter.getCode());
        sqlBuilder.addStringEquals("c.customer_mobile_phone", filter.getPhone());
        sqlBuilder.addDateBetween("p.in_time", filter.getFrom(), filter.getTo());
        sqlBuilder.addOrderByDesc("p.id");

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT p.* " + finalQuery, CustomerOrderPayment.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);
        var lists = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerOrderPayment.class);
        return  Ipage.generator(LIMIT, count, PAGE, lists);
    }
}
