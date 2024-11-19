package vn.flast.domains.order;

import org.apache.commons.lang3.StringUtils;
import vn.flast.utils.NumberUtils;

public record OrderDiscount(String discountUnit, Integer discountValue) {
    public Double getPriceOff(Double subTotal) {
        if(NumberUtils.isNull(discountValue) || StringUtils.isEmpty(discountUnit)) {
            return 0.0;
        }
        if("percent".equals(discountUnit)) {
            return (discountValue * subTotal / 100);
        }
        return Double.valueOf(discountValue);
    }
}
