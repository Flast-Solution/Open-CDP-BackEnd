package vn.flast.entities.warehouse;

import jakarta.validation.constraints.NotNull;
import vn.flast.models.Warehouse;
import java.util.List;

public record SaveStock(
    @NotNull List<SkuDetails> skuDetails,
    @NotNull Warehouse model
){}
