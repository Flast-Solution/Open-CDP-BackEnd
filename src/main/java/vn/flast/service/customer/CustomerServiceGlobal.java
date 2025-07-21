package vn.flast.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.dao.DaoImpl;
import vn.flast.dao.DataDao;
import vn.flast.entities.customer.CustomerFilter;
import vn.flast.entities.customer.CustomerInfo;
import vn.flast.entities.customer.CustomerOrderWithoutDetails;
import vn.flast.models.CustomerOrder;
import vn.flast.repositories.CustomerOrderRepository;
import vn.flast.resultset.CustomerLever;
import vn.flast.models.CustomerEnterprise;
import vn.flast.models.CustomerPersonal;
import vn.flast.models.DataOwner;
import vn.flast.models.User;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataOwnerRepository;
import vn.flast.repositories.UserRepository;
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
    private DataDao dataDao;

    @Autowired
    private DataCareService dataCareService;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private DataOwnerRepository dataOwnerRepository;

    @Autowired
    private UserRepository userRepository;


    public CustomerPersonal save(CustomerPersonal customer){
        return customerRepository.save(customer);
    }

    public CustomerInfo getInfo(String phone) {
        var customer = customerRepository.findByPhone(phone);
        if(customer == null) {
            return null;
        }
        int cId = Math.toIntExact(customer.getId());
        var info = new CustomerInfo();
        info.iCustomer = customer;
        info.lichSuTuongTac = dataDao.lastInteracted(phone);
        var listChuaHt = customerOrderRepository.findByCustomerMobilePhone(customer.getMobile(), CustomerOrder.TYPE_CO_HOI);
        info.donChuaHoanThanh = CopyProperty.copyListIgnoreNull(
            listChuaHt,
            CustomerOrderWithoutDetails::new
        );
        var listDonHoanThanh = customerOrderRepository.findByCustomerMobilePhone(customer.getMobile(), CustomerOrder.TYPE_ORDER);
        List<CustomerOrderWithoutDetails> donHoanThanh = CopyProperty.copyListIgnoreNull(
            listDonHoanThanh,
            CustomerOrderWithoutDetails::new
        );
        info.dataCares = dataCareService.findByCustomerId(cId);
        info.baDonGanNhat = donHoanThanh;
        info.saleTakeCare = saleTakeCare(phone);
        return info;
    }

    private User saleTakeCare(String phone) {
        DataOwner dataOwner = dataOwnerRepository.findByMobile(phone);
        if(dataOwner == null) {
            return null;
        }
        return userRepository.findById(dataOwner.getSaleId()).orElseThrow(
            () -> new RuntimeException("user does not exist")
        );
    }

    public CustomerPersonal findByPhone(String phone) {
        return customerRepository.findByPhone(phone);
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

    public List<?> getCustomerLevel() {
        final String sqlTotalLevel = """
           SELECT COUNT(c.id) AS 'total', c.level AS level FROM `customer_personal` c  WHERE c.`status` = 1 GROUP BY c.level
        """;
        var nativeQuery = entityManager.createNativeQuery(sqlTotalLevel, CustomerLever.REPORT_CUSTOMER_LEVEL);
        return EntityQuery.getListOfNativeQuery(nativeQuery, CustomerLever.class);
    }
}
