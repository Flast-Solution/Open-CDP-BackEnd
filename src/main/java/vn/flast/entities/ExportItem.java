package vn.flast.entities;

import lombok.Getter;
import lombok.Setter;
import vn.flast.models.DetailItem;

import java.util.List;

@Getter
@Setter
public class ExportItem {
    private Integer status = 0;
    private Integer statusConfirm = 0;
    private List<DetailItem> detaiItems;
    private Integer stt;
}
