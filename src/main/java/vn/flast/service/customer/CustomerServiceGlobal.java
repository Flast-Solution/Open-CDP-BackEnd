package vn.flast.service.customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.domains.order.OrderService;
import vn.flast.entities.customer.CustomerFilter;
import vn.flast.entities.customer.CustomerInfo;
import vn.flast.models.*;
import vn.flast.records.CustomerSummary;
import vn.flast.repositories.*;
import vn.flast.pagination.Ipage;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.SqlBuilder;
import java.util.*;

@Service
public class CustomerServiceGlobal {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderService orderService;

    @Autowired
    private FlastNoteRepository noteRepository;

    @Autowired
    private CustomerPersonalRepository customerRepository;

    @Autowired
    private CustomerTagsRepository tagsRepository;

    @Autowired
    private CustomerActivitiesRepository customerActivitiesRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private UserRepository userRepository;

    public CustomerInfo report(Long customerId) {
        var customer = customerRepository.findById(customerId).orElseThrow(
            () -> new RuntimeException("Customer not found")
        );

        CustomerInfo info = new CustomerInfo();
        info.iCustomer = customer;
        info.lead = dataRepository.findFirstByPhone(customer.getMobile()).orElseThrow(
            () -> new RuntimeException("Lead not found, data not async !")
        );
        info.notes = findNotes(customer);
        info.activities = customerActivitiesRepository.findByCustomerId(customer.getId());
        info.orders = findCustomerOrder(customerId, CustomerOrder.TYPE_ORDER);
        info.opportunities = findCustomerOrder(customerId, CustomerOrder.TYPE_CO_HOI);
        info.saleName = saleTakeCare(customer.getSaleId());
        info.summary = summary(customer.getMobile());
        info.tags = tagsRepository.findByCustomerId(customerId);
        return info;
    }

    @SuppressWarnings("CollectionAddAllCanBeReplacedWithConstructor")
    private List<FlastNote> findNotes(CustomerPersonal customer) {
        List<FlastNote> alls = new ArrayList<>();
        alls.addAll(noteRepository.findByMobileOfLead(customer.getMobile()));
        /* Các note khác của order, Kho, CSKH ... */
        return alls;
    }

    private CustomerSummary summary(String mobile) {
        String query = """
        SELECT\s
            IFNULL(SUM(CASE WHEN co.type = 'cohoi' THEN 1 ELSE 0 END), 0) AS opportunities,
            IFNULL(SUM(CASE WHEN co.type = 'order' THEN 1 ELSE 0 END), 0) AS order,
            IFNULL((SELECT COUNT(id) FROM data WHERE customer_mobile = :mobile), 0) AS lead
        FROM customer_order co\s
        WHERE co.customer_mobile_phone = :mobile
        """;
        var nativeQuery = entityManager.createNativeQuery(query, CustomerSummary.class);
        nativeQuery.setParameter("mobile", mobile);
        return (CustomerSummary) nativeQuery.getSingleResult();
    }

    private List<CustomerOrder> findCustomerOrder(Long customerId, String type) {
        var orders = customerOrderRepository.findByCustomerId(customerId, type);
        return orderService.transformDetails(orders);
    }

    private String saleTakeCare(Integer saleId) {
        var user = userRepository.findById(saleId).orElseThrow(
            () -> new RuntimeException("user does not exist")
        );
        return user.getSsoId();
    }

    public Ipage<?> fetchCustomerPersonal(CustomerFilter filter){
        EntityQuery<CustomerPersonal> et = EntityQuery.create(entityManager, CustomerPersonal.class);
        et.setMaxResults(filter.getLimit()).setFirstResult(filter.page() * filter.getLimit());
        et.integerEqualsTo("level",filter.getLevel());
        et.integerEqualsTo("saleId", filter.getSaleId());
        et.stringEqualsTo("email", filter.getEmail());
        et.stringEqualsTo("mobilePhone", filter.getPhone());
        et.addDescendingOrderBy("id");
        List<CustomerPersonal> lists = et.list();
        return Ipage.generator(filter.getLimit(), et.count(), filter.page(), lists);
    }

    public Ipage<?> fetchCustomerEnterprise(CustomerFilter filter){
        int LIMIT = filter.getLimit();
        int OFFSET = filter.page() * LIMIT;
        final String totalSQL = "FROM `customer_enterprise` e left join `customer_order` c on e.id = c.enterprise_id";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);;
        sqlBuilder.addStringEquals("e.email", filter.getEmail());
        sqlBuilder.addStringEquals("e.mobile_phone", filter.getPhone());
        sqlBuilder.addStringEquals("e.tax_code", filter.getTaxCode());
        sqlBuilder.addStringEquals("c.code", filter.getCode());
        sqlBuilder.addNotNUL("e.id");
        sqlBuilder.addOrderByDesc("e.id");

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT e.* " + finalQuery , CustomerEnterprise.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var listData = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerEnterprise.class);
        return Ipage.generator(LIMIT, count, filter.page(), listData);
    }
}
