package vn.flast.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.models.CustomerOrder;

@Getter
@Setter
@NoArgsConstructor
public class ExportInput extends CustomerOrder {

    private Integer status;
    private Long orderId;
    private Integer warehouseId;
    private String note;
    private String createdBy;
    private String exportCode;
    private Integer warehouseDeliveryId;
}
