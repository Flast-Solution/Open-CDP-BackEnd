package vn.flast.searchs;

import vn.flast.utils.NumberUtils;
import java.util.Date;

public record WorkFilter(
    String name,
    Date start,
    Date end,
    Long userId,
    Integer status,
    Integer page
) {
    @Override
    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }
}
