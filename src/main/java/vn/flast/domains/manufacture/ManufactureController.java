package vn.flast.domains.manufacture;
/**************************************************************************/
/*  ManufactureController.java                                            */
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
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.controller.BaseController;
import vn.flast.entities.MyResponse;
import vn.flast.models.ManufactureProduct;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.GenericRepository;
import vn.flast.repositories.ManufactureProductRepository;
import vn.flast.repositories.ManufactureStatusRepository;
import vn.flast.searchs.ManufactureFilter;
import vn.flast.validator.ValidationErrorBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/manufacture")
public class ManufactureController extends BaseController {

    private final ManufactureStatusRepository manufactureStatusRepository;
    private final ManufactureProductRepository manufactureProductRepository;

    @GetMapping("/status")
    public MyResponse<?> allStatus() {
        var status = manufactureStatusRepository.findAll();
        return MyResponse.response(status, "Trạng thái sản suất !");
    }

    @PostMapping("/save")
    public MyResponse<?> save(@Valid @RequestBody ManufactureProduct entity, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Input invalid .!");
        }

        entity.setSsoId(getUserSsoId());
        ManufactureProduct model = manufactureProductRepository.save(entity);
        return MyResponse.response(model, "Cập nhật sản suất thành công !");
    }

    @GetMapping("/fetch")
    public MyResponse<?> list(ManufactureFilter filter) {
        int PAGE = filter.page();
        int LIMIT = 10;
        Sort SORT = Sort.by(Sort.Direction.DESC, "id");
        GenericRepository.SpecificationBuilder<ManufactureProduct> builder = manufactureProductRepository
            .isEqual("code", filter.code());
        Ipage<ManufactureProduct> iPage = builder.toPage(PAGE * LIMIT, LIMIT, SORT);
        return MyResponse.response(iPage);
    }
}
