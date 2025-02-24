package vn.flast.domains.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.exception.InvalidParamsException;
import vn.flast.models.CustomerOrderPayment;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @PostMapping("/manual")
    public MyResponse<?> direct(OrderPaymentInfo paymentInfo) {
        if(!paymentInfo.validate()) {
            throw new InvalidParamsException("Invalid Params Pay .!");
        }
        var data = payService.manualMethod(paymentInfo);
        return MyResponse.response(data);
    }

    @PostMapping("/delete")
    public MyResponse<?> delete(@RequestParam Long id) {
        payService.deleteId(id);
        return MyResponse.response("Ok");
    }

    @GetMapping("/list-by-order-id")
    public MyResponse<?> find(@RequestParam Long orderId) {
        var lists = payService.listByOrderId(orderId);
        return MyResponse.response(lists);
    }

    @PostMapping("/confirm-payment")
    public MyResponse<?> confirmPayment(CustomerOrderPayment input){
        payService.confirmPayment(input);
        return MyResponse.response("oke");
    }
}
