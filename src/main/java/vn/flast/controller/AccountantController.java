package vn.flast.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.domains.order.OrderService;
import vn.flast.domains.payments.OrderPaymentInfo;
import vn.flast.domains.payments.PayService;
import vn.flast.entities.MyResponse;
import vn.flast.entities.payment.PaymentFilter;
import vn.flast.service.AccountantService;

@Log4j2
@RestController
@RequestMapping("/accountant")
public class AccountantController {

    @Autowired
    private AccountantService accountantService;

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/fetch-payment")
    public MyResponse<?> fetchPayment(PaymentFilter filter){
        var data = accountantService.fetch(filter);
        return MyResponse.response(data);
    }

    @PostMapping("/create-payment")
    public MyResponse<?> createPayment(OrderPaymentInfo info){
        payService.create(info);
        return MyResponse.response("oke");
    }
}
