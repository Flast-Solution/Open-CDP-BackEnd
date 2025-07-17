package vn.flast.domains.order;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.CustomerOrder;
import vn.flast.searchs.OrderFilter;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    public MyResponse<?> create(@Valid @RequestBody OrderInput entity, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Input invalid .!");
        }
        return MyResponse.response("");
    }

    @GetMapping("/view")
    public MyResponse<?> view(@RequestParam Long id) {
        var order = orderService.view(id);
        return MyResponse.response(order);
    }

    @GetMapping("/find-by-code")
    public MyResponse<?> findByCode(@RequestParam String code) {
        var order = orderService.findByCode(code);
        return MyResponse.response(order);
    }

    @GetMapping("/fetch")
    public MyResponse<?> list(OrderFilter filter) {
        OrderFilter updatedFilter = filter.withPage(filter.page() + 1).withType(CustomerOrder.TYPE_ORDER);
        var order = orderService.fetchList(updatedFilter);
        return MyResponse.response(order);
    }

    @PostMapping("/complete")
    public MyResponse<?> complete(@RequestParam Long id) {
        var order = orderService.completeOrder(id);
        return MyResponse.response(order);
    }

    @GetMapping("/fetch-by-process")
    public MyResponse<?> fetchByProcess(OrderFilter filter) {
        var orders = orderService.fetchListOrderStatus(filter);
        return MyResponse.response(orders);
    }

    @PostMapping("/update-status-order")
    public MyResponse<?> updateStatusOrder(@RequestParam Long orderId, @RequestParam Integer statusId){
        var order = orderService.updateStatusOrder(orderId, statusId);
        return MyResponse.response(order);
    }

    @PostMapping("/cancel-co-hoi")
    public MyResponse<?> cancelCoHoi(@RequestParam Long orderId, @RequestParam Boolean detail){
        orderService.cancelCoHoi(orderId, detail);
        return MyResponse.response("oke");
    }
}
