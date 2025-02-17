package vn.flast.controller.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.domains.order.OrderInput;
import vn.flast.domains.order.OrderService;
import vn.flast.entities.MyResponse;
import vn.flast.models.CustomerOrder;
import vn.flast.repositories.CustomerOrderRepository;


@Log4j2
@RestController
@RequestMapping("/customer-order")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final OrderService orderService;


    @PostMapping("/sale-create-co-hoi")
    public MyResponse<?> saleCreateOrder(@RequestBody OrderInput input){
        var data = orderService.save(input);
        return MyResponse.response(data);
    }

}
