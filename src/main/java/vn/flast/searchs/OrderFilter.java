package vn.flast.searchs;

import vn.flast.utils.NumberUtils;

public record OrderFilter(
    Integer page,
    Integer limit,
    String code,
    String customerName,
    String customerEmail,
    Integer customerId,
    Integer companyId,
    String customerPhone
) {
    @Override
    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }

    @Override
    public Integer limit() {
        return NumberUtils.isNull(limit) ? 10 : limit;
    }
}
