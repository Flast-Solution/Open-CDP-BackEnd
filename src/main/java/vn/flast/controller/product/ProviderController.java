package vn.flast.controller.product;

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
import vn.flast.models.Provider;
import vn.flast.searchs.ProviderFilter;
import vn.flast.service.ProviderService;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @PostMapping(value = "/save")
    public MyResponse<?> create(@Valid @RequestBody Provider orderStatus, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Input invalid .!");
        }
        providerService.save(orderStatus);
        return  MyResponse.response("Ok");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch(ProviderFilter filter) {
        var data = providerService.fetch(filter);
        return MyResponse.response(data);
    }

    @PostMapping(value = "/delete")
    public MyResponse<?> delete(@RequestParam Long id) {
        providerService.delete(id);
        return  MyResponse.response("Ok");
    }
}
