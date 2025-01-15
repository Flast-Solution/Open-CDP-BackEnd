package vn.flast.service.cskh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.controller.common.BaseController;
import vn.flast.models.DataComplaint;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataComplaintRepository;
import vn.flast.utils.CopyProperty;

import java.util.Optional;

@Service
public class DataComplaintService extends BaseController {

    @Autowired
    private CustomerPersonalRepository customerRepository;

    @Autowired
    private DataComplaintRepository dataComplaintRepository;

    public DataComplaint createComplaint(DataComplaint input){
        input.setStaff(getUsername());
        var customer = customerRepository.findByPhone(input.getCustomerMobile());
        input.setCustomerMobile(Optional.ofNullable(customer.getMobile()).orElse("0"));
        input.setCustomerEmail(Optional.ofNullable(customer.getEmail()).orElse(""));
        input.setCustomerFacebook(Optional.ofNullable(customer.getFacebookId()).orElse("0"));
        input.setCustomerName(Optional.ofNullable(customer.getName()).orElse(""));
        return dataComplaintRepository.save(input);
    }

    public DataComplaint updateComplaint(DataComplaint input){
        var data = dataComplaintRepository.findById(input.getId()).orElseThrow(
                () -> new RuntimeException("no record exists")
        );
        CopyProperty.CopyIgnoreNull(input, data);
        input.setStaff(getUsername());
        return dataComplaintRepository.save(data);
    }
}
