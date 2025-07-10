package vn.flast.searchs;

import vn.flast.utils.NumberUtils;

public record WarehouseFilter(
    Integer page,
    Integer productId
) {
    @Override
    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }
}
