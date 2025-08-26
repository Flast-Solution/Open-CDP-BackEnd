package vn.flast.service.customer;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.domains.order.OrderService;
import vn.flast.entities.customer.CustomerReport;
import vn.flast.models.*;
import vn.flast.records.CustomerSummary;
import vn.flast.records.EnterpriseCountOrder;
import vn.flast.repositories.*;
import vn.flast.pagination.Ipage;
import vn.flast.searchs.CustomerFilter;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.MapUtils;
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

    public CustomerReport report(Long customerId) {
        var customer = customerRepository.findById(customerId).orElseThrow(
            () -> new RuntimeException("Customer not found")
        );

        CustomerReport info = new CustomerReport();
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
        alls.addAll(noteRepository.fetchMobileOfLead(customer.getMobile()));
        /* Các note khác của order, Kho, CSKH ... */
        return alls;
    }

    private CustomerSummary summary(String mobile) {
        String query = """
        SELECT\s
            IFNULL(SUM(CASE WHEN co.type = 'cohoi' THEN 1 ELSE 0 END), 0) AS opportunities,
            IFNULL(SUM(CASE WHEN co.type = 'order' THEN 1 ELSE 0 END), 0) AS orders,
            IFNULL((SELECT COUNT(id) FROM data WHERE customer_mobile = :mobile), 0) AS leads
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
        et.setMaxResults(filter.limit()).setFirstResult(filter.page() * filter.limit());
        et.integerEqualsTo("level",filter.level());
        et.integerEqualsTo("saleId", filter.saleId());
        et.stringEqualsTo("email", filter.email());
        et.stringEqualsTo("mobilePhone", filter.mobile());
        et.addDescendingOrderBy("id");
        List<CustomerPersonal> lists = et.list();
        return Ipage.generator(filter.limit(), et.count(), filter.page(), lists);
    }

    public Ipage<?> fetchCustomerEnterprise(CustomerFilter filter){
        int LIMIT = filter.limit();
        int OFFSET = filter.page() * LIMIT;

        final String totalSQL = "FROM `customer_enterprise` e left join `customer_order` c on e.id = c.enterprise_id";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);

        sqlBuilder.addStringEquals("e.email", filter.email());
        sqlBuilder.addStringEquals("e.mobile_phone", filter.mobile());
        sqlBuilder.addStringEquals("e.tax_code", filter.taxCode());
        sqlBuilder.addStringEquals("c.code", filter.code());
        sqlBuilder.addNotNUL("e.id");
        sqlBuilder.addOrderByDesc("e.id");

        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);

        var nativeQuery = entityManager.createNativeQuery("SELECT e.* " + finalQuery , CustomerEnterprise.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var listData = EntityQuery.<CustomerEnterprise>getListOfNativeQuery(nativeQuery);
        if(listData.isEmpty()) {
            return Ipage.generator(LIMIT, count, filter.page(), listData);
        }

        /* Thêm số lượng đơn hàng và tổng tiền đơn hàng */
        var eIds = listData.stream().map(CustomerEnterprise::getId).toList();
        String queryCountOrder = """
            SELECT enterprise_id as eId , COUNT(*) AS count
            FROM customer_order where enterprise_id IN(:listIds)
            GROUP BY eId
        """;
        var queryCount = entityManager.createNativeQuery(queryCountOrder, EnterpriseCountOrder.class);
        queryCount.setParameter("listIds", eIds);
        var listCounts = EntityQuery.<EnterpriseCountOrder>getListOfNativeQuery(queryCount);
        var mList = MapUtils.mapKeyValue(listCounts, EnterpriseCountOrder::getEId, EnterpriseCountOrder::getCount);
        for(CustomerEnterprise enterprise : listData) {
            enterprise.setNumOfOrder(mList.get(enterprise.getId()));
        }
        return Ipage.generator(LIMIT, count, filter.page(), listData);
    }
}
