package vn.flast.entities.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.utils.NumberUtils;

@Getter
@Setter
@NoArgsConstructor
public class CustomerFilter {
    private String phone;
    private String email;
    private Integer level;
    private Integer saleId;
    private Integer limit;
    private String taxCode;
    private String code;
    private Integer page;

    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }

}
