package vn.flast.entities.lead;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.utils.NumberUtils;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class LeadCareFilter {

    private String phone;
    private String cause;
    private Integer userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date from;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
