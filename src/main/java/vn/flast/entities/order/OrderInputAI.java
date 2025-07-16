package vn.flast.entities.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class OrderInputAI {
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private Long quantity;
    private Long price;
    private List<Long> skuDetailId;
    private String note;
    private Integer bid;
    private Integer vat;
}
