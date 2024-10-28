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
import vn.flast.models.ProductAttributed;
import vn.flast.searchs.ProductAttributedFilter;
import vn.flast.service.ProductAttributedService;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/product-attributed")
public class ProductAttributedController {

    @Autowired
    private ProductAttributedService productAttributedService;

    @PostMapping("/created")
    public MyResponse<?> created(@Valid @RequestBody ProductAttributed input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        var data = productAttributedService.created(input);
        return MyResponse.response(data, "Nhập thành công .!");
    }

    @PostMapping("/updated")
    public MyResponse<?> updated(@Valid @RequestBody ProductAttributed input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        var data = productAttributedService.updated(input);
        return MyResponse.response(data, "Cập nhật thành công .!");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch(ProductAttributedFilter filter) {
        var data = productAttributedService.fetch(filter);
        return MyResponse.response(data);
    }

    @PostMapping("/delete")
    public MyResponse<?> delete(@RequestParam Integer id) {
        productAttributedService.delete(id);
        return MyResponse.response("Xáo bản ghi thành công .!");
    }
}
