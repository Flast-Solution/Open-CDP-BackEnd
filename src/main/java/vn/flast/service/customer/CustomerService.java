package vn.flast.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.entities.customer.CustomerInfo;
import vn.flast.repositories.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

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
        info.customerService = findCusService(cId);
        info.dataCares = dataCareDao.findByCustomerId(cId);
        info.baDonGanNhat = listDonHoanThanh;
        info.saleTakeCare = saleTakeCare(phone);
        return info;
    }
}
