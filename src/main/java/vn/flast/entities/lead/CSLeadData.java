package vn.flast.entities.lead;

import lombok.Getter;
import lombok.Setter;
import vn.flast.models.Data;
import vn.flast.models.DataCare;

@Getter @Setter
public class CSLeadData {
    private DataCare dataCare;
    private Data data;
}
