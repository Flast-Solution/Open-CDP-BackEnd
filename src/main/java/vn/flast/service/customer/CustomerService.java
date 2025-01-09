package vn.flast.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.dao.CustomerOrderDao;
import vn.flast.dao.DataDao;
import vn.flast.entities.OrderStatus;
import vn.flast.entities.customer.CustomerInfo;
import vn.flast.models.CustomerPersonal;
import vn.flast.models.Data;
import vn.flast.models.DataOwner;
import vn.flast.models.User;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataOwnerRepository;
import vn.flast.repositories.UserRepository;
import vn.flast.service.cskh.DataCareService;

@Service
public class CustomerService {

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
}
