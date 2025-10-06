package vn.flast.domains.material;
/**************************************************************************/
/*  InventoryService.java                                                 */
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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.domains.stock.WarehouseService;
import vn.flast.models.MaterialInbound;
import vn.flast.models.MaterialInventory;
import vn.flast.models.Materials;
import vn.flast.repositories.MaterialsInboundRepository;
import vn.flast.repositories.MaterialsInventoryRepository;
import vn.flast.repositories.MaterialsRepository;
import vn.flast.searchs.InventoryFilter;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    /* Services */
    private final WarehouseService warehouseService;

    /* Repository */
    private final MaterialsRepository materialRepository;
    private final MaterialsInventoryRepository inventoryRepository;
    private final MaterialsInboundRepository inboundRepository;

    @Transactional
    public MaterialInbound saveInbound(MaterialInbound model) {

        warehouseService.findById(model.getWarehouseId());
        Materials material = materialRepository.findById(model.getMaterialId()).orElseThrow(
            () -> new RuntimeException("Material not found")
        );

        BigDecimal calculatedQuantity = calculateQuantity(model, material.getUnitType());
        if (calculatedQuantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid quantity for unit type: " + material.getUnitType());
        }
        model.setQuantity(calculatedQuantity);

        inboundRepository.save(model);
        return model;
    }

    public List<MaterialInventory> fetchInventory(InventoryFilter filter) {
        return inventoryRepository.isEqual("materialId", filter.materialId())
            .isEqual("warehouseId", filter.warehouseId()).findAll();
    }

    private BigDecimal calculateQuantity(MaterialInbound dto, UnitType unitType) {
        return switch (unitType) {
            case QUANTITY, WEIGHT -> dto.getQuantity();
            case DIMENSION -> {
                if (dto.getWidth() == null || dto.getHeight() == null || dto.getWidth().compareTo(BigDecimal.ZERO) <= 0 || dto.getHeight().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException("Width and height must be positive for DIMENSION");
                }
                yield dto.getWidth().multiply(dto.getHeight());
            }
            default -> throw new IllegalArgumentException("Unsupported unit type: " + unitType);
        };
    }
}
