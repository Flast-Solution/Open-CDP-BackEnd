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
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/product-material")
public class ProductMaterialController extends BaseController {

    private final ProductMaterialRepository productMaterialRepository;

    @PostMapping("/save/{productId}")
    public MyResponse<?> create(
        @Valid
        @RequestBody List<ProductMaterial> models,
        @PathVariable Long productId
    ) {
        String ssoId = getUserSsoId();
        for(ProductMaterial mPMaterial : models) {
            mPMaterial.setSsoId(ssoId);
        }

        Set<Long> incomingIds = models.stream()
            .map(ProductMaterial::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        List<ProductMaterial> existing = productMaterialRepository.findByProductId(productId);
        List<ProductMaterial> toDelete = existing.stream()
            .filter(e -> e.getId() != null && !incomingIds.contains(e.getId()))
            .collect(Collectors.toList());
        if (!toDelete.isEmpty()) {
            productMaterialRepository.deleteAll(toDelete);
        }

        productMaterialRepository.saveAll(models);
        return MyResponse.response(models, "Cập nhật tồn kho thành công !");
    }

    @GetMapping("/find-by-product/{productId}")
    public MyResponse<?> findProductId(@PathVariable Long productId) {
       var model =  productMaterialRepository.isEqual("productId", productId).findAll();
        return MyResponse.response(model, "Danh sách product material");
    }
}
