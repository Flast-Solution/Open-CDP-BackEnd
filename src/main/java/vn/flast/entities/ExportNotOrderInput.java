package vn.flast.entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExportNotOrderInput {
    private Integer status;
    private Integer warehouseDeliveryId;
    private Integer warehouseReceivingId;
    private String note;
    private String createdBy;
}
