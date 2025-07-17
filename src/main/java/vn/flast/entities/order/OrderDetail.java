package vn.flast.entities.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.entities.warehouse.SkuDetails;
import vn.flast.utils.NumberUtils;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class OrderDetail {
    private String key;
    private Long productId;
    private String orderName;
    private String skuDetailCode;
    private String unit;
    private String warrantyPeriod;
    private Integer quantity;
    private Long price;
    private Long totalPrice;
    private Integer discountRate;
    private Integer discountAmount;
    private List<SkuDetails> mSkuDetails;

    public Integer getDiscountAmount() {
        return NumberUtils.numberWithDefaultZero(discountAmount);
    }
}
