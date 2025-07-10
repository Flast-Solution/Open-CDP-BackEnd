package vn.flast.entities.warehouse;

import jakarta.validation.constraints.NotNull;
import vn.flast.models.Warehouse;

public record SaveStock(
    @NotNull SkuDetails skuDetails,
    @NotNull Warehouse model
){}
