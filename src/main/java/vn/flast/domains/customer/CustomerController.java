package vn.flast.domains.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.searchs.CustomerFilter;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

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
}
