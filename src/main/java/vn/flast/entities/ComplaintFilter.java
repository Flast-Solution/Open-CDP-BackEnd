package vn.flast.entities;

import lombok.Getter;
import lombok.Setter;
import vn.flast.utils.NumberUtils;

@Getter @Setter
public class ComplaintFilter {
    private Integer limit;
    private String phone;
    private Integer saleId;
    private String code;
    private Integer page;
    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }
}
