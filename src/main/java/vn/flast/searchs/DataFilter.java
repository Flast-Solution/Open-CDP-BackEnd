package vn.flast.searchs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vn.flast.utils.NumberUtils;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class DataFilter {

    private Integer saleMember;
    private Integer status;
    private Integer source;
    private Integer partnerId;
    private Integer fromDepartment;
    private Integer saleId;
    private String customerMobile;
    private String customerEmail;
    private String customerOrder;
    private String filterOrderType;
    private String orderCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date from;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date to;
    private Integer page;
    private Boolean ofDaily;

    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }

    private Integer limit;

    public Boolean ofDaily() {
        return ofDaily != null && ofDaily;
    }
}