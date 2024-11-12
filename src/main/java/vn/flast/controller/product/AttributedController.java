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
import vn.flast.models.Attributed;
import vn.flast.models.AttributedValue;
import vn.flast.repositories.AttributedValueRepository;
import vn.flast.searchs.AttributedFilter;
import vn.flast.service.AttributedService;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/attributed")
public class AttributedController {

    @Autowired
    private AttributedService attributedService;

    @Autowired
    private AttributedValueRepository valueRepository;

    @PostMapping("/save")
    public MyResponse<?> created(@Valid  @RequestBody Attributed input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Invalid input .!");
        }
        var attributed = attributedService.save(input);
        return MyResponse.response(attributed, "Tạo mới thuộc tính thành công .!");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetch(AttributedFilter filter) {
        var data = attributedService.fetch(filter);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-value-by-id")
    public MyResponse<?> fetchValueById(AttributedFilter input) {
        var data = attributedService.fetchAttributedValue(input);
        return MyResponse.response(data);
    }

    @PostMapping("/save-value-by-id")
    public MyResponse<?> saveValueById(@Valid  @RequestBody AttributedValue input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Invalid input .!");
        }
        valueRepository.findAttrIdAndValue(input.getAttributedId(), input.getValue()).ifPresentOrElse(
            (item) -> {},
            () -> valueRepository.save(input)
        );
        return MyResponse.response(input, "Tạo mới thuộc tính thành công .!");
    }

    @PostMapping("/delete")
    public MyResponse<?> delete(@RequestParam Long id) {
        attributedService.delete(id);
        return MyResponse.response("Xáo bản ghi thành công .!");
    }
}
