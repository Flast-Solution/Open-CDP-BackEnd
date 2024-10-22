package vn.flast.dtos;

import vn.flast.utils.NumberUtils;

public record UserFilter(
    String fullName,
    String ssoId,
    Integer id,
    Integer page
) {
    @Override
    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }
}
