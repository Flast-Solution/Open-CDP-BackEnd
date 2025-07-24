package vn.flast.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.dao.DaoImpl;
import vn.flast.dao.DataDao;
import vn.flast.entities.customer.CustomerFilter;
import vn.flast.entities.customer.CustomerInfo;
import vn.flast.entities.customer.CustomerOrderWithoutDetails;
import vn.flast.models.CustomerOrder;
import vn.flast.repositories.*;
import vn.flast.models.CustomerEnterprise;
import vn.flast.models.CustomerPersonal;
import vn.flast.pagination.Ipage;
import vn.flast.service.cskh.DataCareService;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.SqlBuilder;
import java.util.List;

@Service
public class CustomerServiceGlobal extends DaoImpl<Integer, CustomerEnterprise> {

    @Autowired
    private CustomerPersonalRepository customerRepository;

    @Autowired
    private CustomerActivitiesRepository customerActivitiesRepository;

    @Autowired
    private DataDao dataDao;

    @Autowired
    private DataCareService dataCareService;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private DataOwnerRepository dataOwnerRepository;

    @Autowired
    private UserRepository userRepository;

    public CustomerInfo getInfo(Long customerId) {
        var customer = customerRepository.findById(customerId).orElseThrow(
            () -> new RuntimeException("Customer not found")
        );
        int cId = Math.toIntExact(customer.getId());

        CustomerInfo info = new CustomerInfo();
        info.iCustomer = customer;
        info.activities = customerActivitiesRepository.findByCustomerId(customer.getId());

        var orders = customerOrderRepository.findByCustomerMobilePhone(customer.getMobile(), CustomerOrder.TYPE_ORDER);
        List<CustomerOrderWithoutDetails> orderNoDetails = CopyProperty.copyListIgnoreNull(
            orders,
            CustomerOrderWithoutDetails::new
        );
        info.dataCares = dataCareService.findByCustomerId(cId);
        info.orders = orderNoDetails;
        info.saleName = saleTakeCare(customer.getSaleId());
        return info;
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
