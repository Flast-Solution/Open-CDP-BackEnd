package vn.flast.controller.product;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/




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
import vn.flast.repositories.AttributedRepository;
import vn.flast.repositories.AttributedValueRepository;
import vn.flast.searchs.AttributedFilter;
import vn.flast.service.AttributedService;
import vn.flast.validator.ValidationErrorBuilder;

import java.util.List;

@RestController
@RequestMapping("/attributed")
public class AttributedController {

    @Autowired
    private AttributedService attributedService;

    @Autowired
    private AttributedValueRepository valueRepository;

    @Autowired
    private AttributedRepository attributedRepository;

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

    @GetMapping("/fetch-attr-ids")
    public MyResponse<?> fetchByIDs(@RequestParam List<Long> ids) {
        var data = attributedRepository.findAllById(ids);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-value-by-id")
    public MyResponse<?> fetchValueById(AttributedFilter input) {
        var data = attributedService.fetchAttributedValue(input);
        return MyResponse.response(data);
    }

    @GetMapping("/fetch-value-by-ids")
    public MyResponse<?> fetchValueByIds(@RequestParam List<Long> ids) {
        var data = valueRepository.findAllById(ids);
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
