package vn.flast.entities.warehouse;

import jakarta.validation.constraints.NotNull;
import vn.flast.models.WarehouseProduct;
import java.util.List;

public record SaveStock(
    @NotNull List<SkuDetails> mSkuDetails,
    @NotNull WarehouseProduct model
){}
