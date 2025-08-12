package vn.flast.searchs;

import vn.flast.utils.NumberUtils;
import java.util.Date;

public record ShipFilter (
    String orderCode,
    String detailCode,
    Integer transporterId,
    String transporterCode,
    Integer page,
    Integer status,
    Date from,
    Date to
) {
    @Override
    public Integer page() {
        return NumberUtils.isNull(page) ? 1 : (page - 1);
    }
}
