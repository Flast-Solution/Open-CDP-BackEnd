package vn.flast.domains.payments;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/

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
    Integer isNeedConfirm,
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

    public Integer getConfirm() {
        return NumberUtils.isNull(isNeedConfirm) ? 1 : 0;
    }
}
