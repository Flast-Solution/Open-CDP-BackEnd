package vn.flast.domains.order;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.CustomerOrderStatus;
import vn.flast.repositories.CustomerOrderStatusRepository;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/order-status")
public class OrderStatusController {

    @Autowired
    private CustomerOrderStatusRepository statusRepository;

    @PostMapping(value = "/save")
    public MyResponse<?> create(@Valid @RequestBody CustomerOrderStatus orderStatus, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Input invalid .!");
        }
        statusRepository.save(orderStatus);
        return  MyResponse.response("Ok");
    }

    @GetMapping(value = "/fetch")
    public MyResponse<?> fetch() {
        return  MyResponse.response(statusRepository.findAll(Sort.by("order")));
    }
}
