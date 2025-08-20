package vn.flast.searchs;

import org.springframework.format.annotation.DateTimeFormat;
import vn.flast.utils.NumberUtils;
import java.util.Date;

public record DataCollectionFilter(
    String address,
    Integer status,
    Integer userId,
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date start,
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date end,
    String channel,
    Integer number,
    String groupName,
    Integer page,
    String withMobile,
    Integer limit
) {
    @Override
    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }

    public boolean onlyMobile() {
        return Boolean.parseBoolean(withMobile);
    }

    @Override
    public Integer limit() {
        return NumberUtils.isNull(limit) ? 500 : limit;
    }
}
