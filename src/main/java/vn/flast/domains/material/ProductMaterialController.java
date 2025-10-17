package vn.flast.domains.material;
/**************************************************************************/
/*  ProductMaterialController.java                                        */
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.controller.BaseController;
import vn.flast.entities.MyResponse;
import vn.flast.models.ProductMaterial;
import vn.flast.repositories.ProductMaterialRepository;
import vn.flast.validator.ValidationErrorBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/product-material")
public class ProductMaterialController extends BaseController {

    private final ProductMaterialRepository productMaterialRepository;

    @PostMapping("/save")
    public MyResponse<?> create(@Valid @RequestBody ProductMaterial entity, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Input invalid .!");
        }

        entity.setSsoId(getUserSsoId());
        ProductMaterial model = productMaterialRepository.save(entity);
        return MyResponse.response(model, "Cập nhật tồn kho thành công !");
    }

    @GetMapping("/find-by-product/{id}")
    public MyResponse<?> findProductId(@PathVariable Long productId) {
       var model =  productMaterialRepository.isEqual("productId", productId).findAll();
        return MyResponse.response(model, "Cập nhật tồn kho thành công !");
    }

    @PostMapping("/delete/{id}")
    public MyResponse<?> delete(@PathVariable Long id) {
        productMaterialRepository.deleteById(id);
        return MyResponse.response("Oki", "Xóa định mức sản phẩm thành công !");
    }
}
