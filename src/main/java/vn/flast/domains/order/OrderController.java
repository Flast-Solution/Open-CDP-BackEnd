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
        var order = orderService.save(entity);
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

    @GetMapping("/list")
    public MyResponse<?> list(OrderFilter filter) {
        var order = orderService.fetchList(filter);
        return MyResponse.response(order);
    }
}
