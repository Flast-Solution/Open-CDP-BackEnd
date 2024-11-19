package vn.flast.domains.order;

import vn.flast.models.CustomerOrderPayment;

public record OrderPaymentInfo(
    Double amount,
    String payType,
    String content
) {
    public void transformPayment(CustomerOrderPayment payment) {
        payment.setAmount(amount);
        payment.setMethod(payType);
        payment.setContent(content);
    }
}
