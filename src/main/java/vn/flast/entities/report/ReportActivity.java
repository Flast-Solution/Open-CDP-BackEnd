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
    private List<?> ActivitySale;
    private List<?> ActivityGroup;
    private List<?> ActivityCustomer;
    private List<?> ActivityOrder;
    private List<?> ActivityProduct;
}
