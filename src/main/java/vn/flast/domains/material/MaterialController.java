package vn.flast.domains.material;
/**************************************************************************/
/*  MaterialController.java                                               */
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.controller.BaseController;
import vn.flast.entities.MyResponse;
import vn.flast.models.Materials;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.GenericRepository;
import vn.flast.repositories.MaterialsInboundRepository;
import vn.flast.repositories.MaterialsInventoryRepository;
import vn.flast.repositories.MaterialsOutboundRepository;
import vn.flast.repositories.MaterialsRepository;
import vn.flast.repositories.ProductMaterialRepository;
import vn.flast.searchs.InventoryFilter;
import vn.flast.validator.ValidationErrorBuilder;

@AllArgsConstructor
@RestController
@RequestMapping("/material")
public class MaterialController extends BaseController {

    private final MaterialsRepository materialsRepository;
    private final MaterialsInboundRepository inboundRepository;
    private final MaterialsOutboundRepository outboundRepository;
    private final MaterialsInventoryRepository inventoryRepository;
    private final ProductMaterialRepository productMaterialRepository;

    @PostMapping("/save")
    public MyResponse<?> create(@Valid @RequestBody Materials entity, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Input invalid .!");
        }
        Materials model = materialsRepository.save(entity);
        return MyResponse.response(model, "Cập nhật nguyên vật liệu thành công !");
    }

    @GetMapping("/fetch")
    public MyResponse<?> list(InventoryFilter filter) {
        int PAGE = filter.page();
        int LIMIT = 10;
        Sort SORT = Sort.by(Sort.Direction.DESC, "id");
        GenericRepository.SpecificationBuilder<Materials> builder = materialsRepository
            .fetch("inventory")
            .like("name", filter.name())
            .isEqual("inventory.warehouseId", filter.warehouseId());
        Ipage<Materials> iPage = builder.toPage(PAGE * LIMIT, LIMIT, SORT);
        return MyResponse.response(iPage);
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public MyResponse<?> delete(@PathVariable Long id) {
        inboundRepository.deleteByMaterialId(id);
        outboundRepository.deleteByMaterialId(id);
        inventoryRepository.deleteByMaterialId(id);
        productMaterialRepository.deleteByMaterialId(id);
        materialsRepository.deleteById(id);
        return MyResponse.response("Oki", "Cập nhật nguyên vật liệu thành công !");
    }
}
