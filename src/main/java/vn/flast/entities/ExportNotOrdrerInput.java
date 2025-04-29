package vn.flast.entities;

import lombok.Getter;
import lombok.Setter;
import vn.flast.models.DetailItem;

import java.util.List;

@Getter
@Setter
public class ExportNotOrdrerInput {

    private Integer status;
    private Integer warehouseDeliveryId;
    private Integer warehouseReceivingId;
    private String note;
    private String createdBy;
    private List<DetailItem> items;
}
