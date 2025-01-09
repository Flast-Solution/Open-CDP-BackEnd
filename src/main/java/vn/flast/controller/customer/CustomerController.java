package vn.flast.controller.customer;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.service.customer.CustomerService;

@Log4j2
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/find-by-phone")
    public MyResponse<?> listDataStatus(
            @RequestParam(name = "phone") String phone,
            @RequestParam(defaultValue = "withOrder") String withOrder
    ) {
        var data = ("withOrder".endsWith(withOrder) ? customerService.getInfo(phone) : customerService.findByPhone(phone));
        return MyResponse.response(data);
    }
}
