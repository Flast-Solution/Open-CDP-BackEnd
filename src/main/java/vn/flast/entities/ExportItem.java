package vn.flast.entities;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import vn.flast.models.DetailItem;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ExportItem {
    private Integer status = 0;
    private Integer statusConfirm = 0;
    private Date createdDate;
    private Date updateDate;
    private String note;
    private List<DetailItem> detaiItems;
    private Integer warehouseDeliveryId;
    private Integer stt;
}
