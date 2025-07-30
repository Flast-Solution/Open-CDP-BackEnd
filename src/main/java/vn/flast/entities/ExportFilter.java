package vn.flast.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.utils.NumberUtils;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class ExportFilter {

    private Integer warehouseId;
    private String orderDetailCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date from;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date to;

    private Integer page;
    private Integer limit;

    public Integer page() {
        return NumberUtils.isNull(page) ? 0 : (page - 1);
    }
}
