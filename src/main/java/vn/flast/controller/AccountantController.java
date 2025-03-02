package vn.flast.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.entities.PaymentFilter;
import vn.flast.service.AccountantService;

@Log4j2
@RestController
@RequestMapping("/accountant")
public class AccountantController {

    @Autowired
    private AccountantService accountantService;

    @GetMapping("/fetch-payment")
    public MyResponse<?> fetchPayment(PaymentFilter filter){
        var data = accountantService.fetch(filter);
        return MyResponse.response(data);
    }


}
