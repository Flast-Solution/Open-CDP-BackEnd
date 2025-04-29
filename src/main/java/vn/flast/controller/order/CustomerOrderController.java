package vn.flast.controller.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.domains.order.OrderInput;
import vn.flast.domains.order.OrderService;
import vn.flast.entities.MyResponse;
import vn.flast.models.CustomerOrder;
import vn.flast.repositories.CustomerOrderRepository;
import vn.flast.searchs.OrderFilter;
import vn.flast.service.DataService;
import vn.flast.utils.NumberUtils;


@Log4j2
@RestController
@RequestMapping("/customer-order")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final OrderService orderService;


    private final DataService dataService;


    @PostMapping("/sale-create-co-hoi")
    public MyResponse<?> saleCreateOrder(@RequestBody OrderInput input){
        var data = orderService.create(input);
        if(NumberUtils.isNotNull(input.dataId())){
            dataService.updateStatusCohoi(input.dataId());
        }
        return MyResponse.response(data);
    }

//    @PostMapping("/create-flast-order")
//    public MyResponse<?> createFlastOrder(){
//
//    }

    @GetMapping("/fetch-cohoi")
    public MyResponse<?> fetchCohoi(OrderFilter filter) {
        OrderFilter updatedFilter = filter.withPage(filter.page() + 1).withType(CustomerOrder.TYPE_CO_HOI);
        return MyResponse.response(orderService.fetchList(updatedFilter));
    }


    @PostMapping("/update-cohoi")
    public MyResponse<?> updateCohoi(@RequestBody OrderInput input){
        var data = orderService.updateOrder(input);
        return MyResponse.response(data);
    }

    @PostMapping("/cancel-cohoi")
    public MyResponse<?> cancelCohoi(@RequestParam Long orderId, @RequestParam Boolean detail){
        orderService.cancelCohoi(orderId, detail);
        return MyResponse.response("oke");
    }

    @GetMapping("/find-id")
    public MyResponse<?> findById(@RequestParam Long orderId) {
        var order = orderService.findById(orderId);
        return MyResponse.response(order);
    }
}
