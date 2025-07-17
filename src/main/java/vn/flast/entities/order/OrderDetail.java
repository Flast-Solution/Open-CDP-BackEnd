package vn.flast.entities.order;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String productName;
    private String orderName;
    private String skuDetailCode;
    private String unit;
    private String warrantyPeriod;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
    private Integer discountRate;
    private Double discountAmount;

    @JsonProperty("mSkuDetails")
    private List<SkuDetails> skuDetails;

    public Double getDiscountAmount() {
        return NumberUtils.gteZero(discountAmount) ? discountAmount : 0.0;
    }
}
