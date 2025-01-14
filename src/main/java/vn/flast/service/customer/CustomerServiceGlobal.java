package vn.flast.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.dao.CustomerOrderDao;
import vn.flast.dao.DaoImpl;
import vn.flast.dao.DataDao;
import vn.flast.entities.OrderStatus;
import vn.flast.entities.customer.CustomerFilter;
import vn.flast.entities.customer.CustomerInfo;
import vn.flast.resultset.CustomerLever;
import vn.flast.models.CustomerEnterprise;
import vn.flast.models.CustomerPersonal;
import vn.flast.models.Data;
import vn.flast.models.DataOwner;
import vn.flast.models.User;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataOwnerRepository;
import vn.flast.repositories.UserRepository;
import vn.flast.service.cskh.DataCareService;
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
    private CustomerOrderDao customerOrderDao;

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
        info.donChuaHoanThanh = customerOrderDao.findByStatusAndCustomer(OrderStatus.BAO_GIA, cId);
        var listDonHoanThanh = customerOrderDao.findByStatusAndCustomer(OrderStatus.ACOUNTANT_HOAN_THANH, cId);
//        info.customerService = findCusService(cId);
        info.dataCares = dataCareService.findByCustomerId(cId);
        info.baDonGanNhat = listDonHoanThanh;
        info.saleTakeCare = saleTakeCare(phone);
        return info;
    }

    private User saleTakeCare(String phone) {
        DataOwner dataOwner = dataOwnerRepository.findByMobile(phone);
        if(dataOwner == null) {
            return null;
        }
        return userRepository.findById(dataOwner.getSaleId().intValue()).orElseThrow(
                () -> new RuntimeException("user does not exist")
        );
    }

    public CustomerPersonal findByPhone(String phone){
        var customer = customerRepository.findByPhone(phone);
        return customer;
    }

    public CustomerPersonal createCustomer(Data data) {
        CustomerPersonal model = new CustomerPersonal();
        model.setSourceId(Long.valueOf(data.getSource().longValue()));
        model.setEmail(data.getCustomerEmail());
        model.setName(data.getCustomerName());
        model.setSaleId(data.getSaleId());
        model.setMobile(data.getCustomerMobile());
        customerRepository.save(model);
        return model;
    }

    public Ipage<?> fetchCustomerPersonal(CustomerFilter filter){
        EntityQuery<CustomerPersonal> et = EntityQuery.create(entityManager, CustomerPersonal.class);
        et.setMaxResults(filter.getLimit()).setFirstResult(filter.getPage() * filter.getLimit());
        et.integerEqualsTo("level",filter.getLevel());
        et.integerEqualsTo("saleId", filter.getSaleId());
        et.stringEqualsTo("email", filter.getEmail());
        et.stringEqualsTo("mobilePhone", filter.getPhone());
        et.addDescendingOrderBy("id");
        List<CustomerPersonal> results = et.list();
        long countItems = et.count();
        return new Ipage<>(filter.getLimit(), Math.toIntExact(countItems), filter.getPage(), results);
    }

    public Ipage<?> fetchCustomerEnterprise(CustomerFilter filter){
        int LIMIT = filter.getLimit();
        int OFFSET = ( filter.page() - 1 ) * LIMIT;
        final String totalSQL = "FROM `customer_enterprise` e left join `customer_order` c on e.id = c.enterprise_id";
        SqlBuilder sqlBuilder = SqlBuilder.init(totalSQL);;
        sqlBuilder.addStringEquals("e.email", filter.getEmail());
        sqlBuilder.addStringEquals("e.mobile_phone", filter.getPhone());
        sqlBuilder.addStringEquals("e.tax_code", filter.getTaxCode());
        sqlBuilder.addStringEquals("c.code", filter.getCode());
        sqlBuilder.addNotNUL("e.id");
        sqlBuilder.addDesc("e.id");
        String finalQuery = sqlBuilder.builder();
        var countQuery = entityManager.createNativeQuery(sqlBuilder.countQueryString());
        Long count = sqlBuilder.countOrSumQuery(countQuery);
        var nativeQuery = entityManager.createNativeQuery("SELECT e.* " + finalQuery , CustomerEnterprise.class);
        nativeQuery.setMaxResults(LIMIT);
        nativeQuery.setFirstResult(OFFSET);

        var listData = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerEnterprise.class);
        return Ipage.generator(LIMIT, count, filter.page(), listData);
    }

    public List<?> getCustomerLevel(){
        final String sqlTotallevel = """
                SELECT COUNT(c.id) AS 'total', c.level AS level FROM `customer_personal` c  WHERE c.`status` = 1 GROUP BY c.level
                """;
        var nativeQuery = entityManager.createNativeQuery(sqlTotallevel, CustomerLever.REPORT_CUSTOMER_LEVEL);
        List<CustomerLever> iList = EntityQuery.getListOfNativeQuery(nativeQuery, CustomerLever.class);
        return iList;

    }


}
