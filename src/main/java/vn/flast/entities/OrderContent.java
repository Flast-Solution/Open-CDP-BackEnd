package vn.flast.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.models.CustomerOrder;

@Getter
@Setter
@NoArgsConstructor
public class OrderContent extends OrderResponse {

    private String content;
    private String infoCustomer;
    private String infoSale;
    private String infoPrice;
}
