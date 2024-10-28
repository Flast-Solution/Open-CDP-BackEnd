package vn.flast.searchs;

import vn.flast.utils.NumberUtils;

public record ProductAttributedFilter(
    String name,
    Integer productId,
    Integer page
) {
    @Override
    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }
}
