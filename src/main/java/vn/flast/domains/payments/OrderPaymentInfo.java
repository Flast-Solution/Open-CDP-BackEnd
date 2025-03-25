package vn.flast.domains.payments;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.With;
import org.apache.commons.lang3.StringUtils;
import vn.flast.models.CustomerOrderPayment;
import vn.flast.utils.NumberUtils;
import java.util.Date;

public record OrderPaymentInfo(
    Double amount,
    @With Long id,
    String method,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date datePay,
    String content,
    @With Boolean status
) {
    public void transformPayment(CustomerOrderPayment payment) {
        payment.setAmount(amount);
        if(StringUtils.isNotEmpty(method)) {
            payment.setMethod(method);
        }
        payment.setContent(content);

    }

    public boolean validate() {
        if(NumberUtils.isNull(id) || StringUtils.isEmpty(method)) {
            return false;
        }
        return !NumberUtils.isNull(amount);
    }
}
