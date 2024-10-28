package vn.flast.controller.order;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.CustomerOrderStatus;
import vn.flast.repositories.CustomerOrderStatusRepository;
import vn.flast.validator.ValidationErrorBuilder;

@Log4j2
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
}
