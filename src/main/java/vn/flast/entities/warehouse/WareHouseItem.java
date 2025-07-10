package vn.flast.entities.warehouse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class WareHouseItem {
    private Long productId;
    private Long providerId;
    private String productName;
    private Long skuId;
    private String skuInfo;
    private Integer fee;
    private Long quantity;
    private Long priceRef;
    private String discount;
}
