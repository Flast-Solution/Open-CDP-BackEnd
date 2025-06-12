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
import vn.flast.domains.payments.OrderPaymentInfo;
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
        var order = orderService.create(entity);
        return MyResponse.response(order);
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
        OrderFilter updatedFilter = filter.withPage(filter.page() + 1).withType(CustomerOrder.TYPE_ORDER);
        var orders = orderService.fetchListOrderStatus(filter);
        return MyResponse.response(orders);
    }
}
