package vn.flast.domains.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.searchs.CustomerFilter;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerPersonalRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private vn.flast.service.customer.CustomerService cusService;

    @GetMapping("/find")
    public MyResponse<?> find(CustomerFilter filter) {
        var customers = customerService.find(filter);
        return MyResponse.response(customers);
    }

    @GetMapping("/find-id")
    public MyResponse<?> findById(@RequestParam Long customerId) {
        var customers = customerRepository.findById(customerId);
        return MyResponse.response(customers);
    }

    @GetMapping("/find-by-phone")
    public MyResponse<?> listDataStatus(
            @RequestParam(name = "phone") String phone,
            @RequestParam(defaultValue = "withOrder") String withOrder
    ) {
        var data = ("withOrder".endsWith(withOrder) ? cusService.getInfo(phone) : cusService.findByPhone(phone));
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-customer-personal")
    public MyResponse<?> fetchCustomerPersonal(vn.flast.entities.customer.CustomerFilter filter){
        var data = cusService.fetchCustomerPersonal(filter);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-customer-enterprise")
    public MyResponse<?> fetchCustomerEnterPrise(vn.flast.entities.customer.CustomerFilter filter){
        var data = cusService.fetchCustomerEnterprise(filter);
        return MyResponse.response(data);
    }

    @GetMapping("/count-level-customer")
    public MyResponse<?> countLevelCustomer(){
        var data = cusService.getCustomerLevel();
        return MyResponse.response(data);
    }
}
