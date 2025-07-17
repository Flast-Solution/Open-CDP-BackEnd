package vn.flast.entities;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter @Setter
public class ExportItem {
    private Integer status = 0;
    private Integer statusConfirm = 0;
    private Date createdDate;
    private Date updateDate;
    private String note;
    private Integer warehouseDeliveryId;
    private Integer stt;
}
