package vn.flast.searchs;

import vn.flast.utils.NumberUtils;

public record WarehouseFilter(
    Integer page,
    String skuHash,
    Integer productId,
    Integer skuId,
    Integer stockId,
    Integer providerId,
    Integer limit
) {
    @Override
    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }

    @Override
    public Integer limit() {
        return NumberUtils.isNull(limit) ? 20 : (limit > 200 ? 200 : limit);
    }
}
