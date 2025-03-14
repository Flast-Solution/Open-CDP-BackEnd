package vn.flast.searchs;

import vn.flast.utils.NumberUtils;

public record ProviderFilter(
    String name,
    String mobile,
    Integer page,
    Integer limit
) {
    @Override
    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }
}
