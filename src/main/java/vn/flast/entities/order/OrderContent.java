package vn.flast.entities.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderContent extends OrderResponse {

    private String content;
    private String infoCustomer;
    private String infoSale;
    private String infoPrice;
}
