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
