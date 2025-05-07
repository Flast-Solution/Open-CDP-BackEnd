package vn.flast.entities.report;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReportActivity {
    private List<?> ActivityLead;
    private List<?> ActivityRevenue;
}
