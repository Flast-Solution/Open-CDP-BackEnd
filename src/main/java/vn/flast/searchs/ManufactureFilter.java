package vn.flast.searchs;

import vn.flast.utils.NumberUtils;

public record ManufactureFilter(
    String code,
    Integer page
){
    @Override
    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }
}
