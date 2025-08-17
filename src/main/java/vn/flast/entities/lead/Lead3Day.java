package vn.flast.entities.lead;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@Setter @Getter
public class Lead3Day {
    private List<String> issues;
    private Integer rating;
    private Integer satisfactionPercent;
    private String newFeatures;
    private String supportRequest;
}
