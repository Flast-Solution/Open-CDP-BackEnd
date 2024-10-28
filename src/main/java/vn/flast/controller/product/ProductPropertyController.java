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
import vn.flast.models.ProductProperty;
import vn.flast.searchs.AttributedFilter;
import vn.flast.service.ProductPropertyService;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/product-property")
public class ProductPropertyController {

    @Autowired
    private ProductPropertyService productpropertyService;

    @PostMapping("/save")
    public MyResponse<?> created(@Valid @RequestBody ProductProperty input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        var data = productpropertyService.created(input);
        return MyResponse.response(data, "Nhập thành công .!");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch(AttributedFilter filter) {
        var data = productpropertyService.fetch(filter);
        return MyResponse.response(data);
    }

    @PostMapping("/delete")
    public MyResponse<?> delete(@RequestParam Integer id) {
        productpropertyService.delete(id);
        return MyResponse.response("Xáo bản ghi thành công .!");
    }
}
