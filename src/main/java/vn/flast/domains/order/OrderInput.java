package vn.flast.domains.order;

import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.JsonUtils;
import vn.flast.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public record OrderInput(
    Long id,
    Long customerId,
    String customerName,
    String customerEmail,
    String customerPhone,
    OrderDiscount discount,
    OrderPaymentInfo paymentInfo,
    Integer vat,
    Double subTotal,
    Double total,
    List<CustomerOrderDetail> orderDetails,
    String note
) {
    public void transformOrder(CustomerOrder order) {
        if(Common.CollectionIsEmpty(orderDetails)) {
            throw new RuntimeException("Order detail is required .!");
        }
        order.setId(id);
        order.setCustomerId(customerId);
        order.setCustomerReceiverName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setCustomerMobilePhone(customerPhone);
        order.setDiscountInfo(JsonUtils.toJson(discount));
        order.setSubtotal(subTotal);
        order.setTotal(total);
        order.setCustomerNote(note);
        if(NumberUtils.isNotNull(vat)) {
            order.setVat(vat);
        }
        order.setPaid(Optional.ofNullable(paymentInfo).map(OrderPaymentInfo::amount).orElse(0.));
        order.setPriceOff(discount.getPriceOff(subTotal));
    }

    public List<CustomerOrderDetail> transformOnCreateDetail(CustomerOrder order) {
        List<CustomerOrderDetail> detailList = new ArrayList<>();
        int i = 1;
        for(CustomerOrderDetail detailOrder : orderDetails) {
            CustomerOrderDetail detail = new CustomerOrderDetail();
            CopyProperty.CopyIgnoreNull(detailOrder, detail);
            detail.setCustomerOrderId(order.getId());
            detail.setCode(order.getCode().concat("-" + i));
            detail.setCreatedAt(new Date());
            detailList.add(detail);
            detail.setStatus(order.getStatus());
            i++;
        }
        return detailList;
    }
}
