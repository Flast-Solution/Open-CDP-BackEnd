package vn.flast.entities.warehouse;

import lombok.Data;
import java.util.List;

@Data
public class SkuDetails {
    private String text;
    private List<Values> values;

    @Data
    public static class Values {
        private int id;
        private String text;
    }
}
