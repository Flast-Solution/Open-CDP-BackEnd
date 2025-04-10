package vn.flast.entities;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.models.DetailItem;
import vn.flast.models.Warehouse;

@Getter
@Setter
@NoArgsConstructor
public class ExportProduct extends DetailItem {

    private Integer warehouseId;
    private Warehouse warehouse;
}
