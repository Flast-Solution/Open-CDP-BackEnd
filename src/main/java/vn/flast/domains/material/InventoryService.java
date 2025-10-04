package vn.flast.domains.material;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.models.MaterialInbound;
import vn.flast.models.Materials;
import vn.flast.repositories.MaterialsInventoryRepository;
import vn.flast.repositories.MaterialsRepository;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final MaterialsRepository materialRepository;
    private final MaterialsInventoryRepository inventoryRepository;

    @Transactional
    public MaterialInbound processInbound(MaterialInbound dto) {

        Materials material = materialRepository.findById(dto.getMaterialId()).orElseThrow(
            () -> new RuntimeException("Material not found")
        );

        BigDecimal calculatedQuantity = calculateQuantity(dto, material.getUnitType());
        if (calculatedQuantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid quantity for unit type: " + material.getUnitType());
        }
        dto.setQuantity(calculatedQuantity);
        return dto;
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
