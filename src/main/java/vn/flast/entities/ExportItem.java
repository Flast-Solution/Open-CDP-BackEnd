package vn.flast.entities;

import lombok.Getter;
import lombok.Setter;
import vn.flast.models.DetailItem;

import java.util.List;

@Getter
@Setter
public class ExportItem {
    private List<DetailItem> detaiItems;
    private Integer stt;
}
