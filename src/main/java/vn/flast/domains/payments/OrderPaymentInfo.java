package vn.flast.domains.payments;

import org.apache.commons.lang3.StringUtils;
import vn.flast.models.CustomerOrderPayment;
import vn.flast.utils.NumberUtils;
import java.util.Date;

public record OrderPaymentInfo(
    Double amount,
    Long orderId,
    String method,
    Date date,
    String content,
    Integer vat,
    Integer shippingCost
) {
    public void transformPayment(CustomerOrderPayment payment) {
        payment.setAmount(amount);
        if(StringUtils.isNotEmpty(method)) {
            payment.setMethod(method);
        }
        payment.setContent(content);
    }

    public boolean validate() {
        if(NumberUtils.isNull(orderId) || StringUtils.isEmpty(method)) {
            return false;
        }
        return NumberUtils.gteZero(amount);
    }
}
