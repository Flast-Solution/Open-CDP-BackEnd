package vn.flast.domains.order;

import vn.flast.domains.payments.OrderPaymentInfo;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import vn.flast.models.CustomerPersonal;
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
    CustomerPersonal customer,
    OrderDiscount discount,
    OrderPaymentInfo paymentInfo,
    Integer vat,
    List<CustomerOrderDetail> details,
    String note
) {
    public void transformOrder(CustomerOrder order) {
        if(Common.CollectionIsEmpty(details)) {
            throw new RuntimeException("Order detail is required .!");
        }
        order.setId(id);
        order.setCustomerId(customer.getId());
        order.setCustomerReceiverName(customer.getName());
        order.setCustomerEmail(customer.getEmail());
        order.setCustomerMobilePhone(customer.getMobile());
        order.setDiscountInfo(JsonUtils.toJson(discount));
        order.setCustomerNote(note);
        order.setOpportunityAt(new Date());
        if(NumberUtils.isNotNull(vat)) {
            order.setVat(vat);
        }
        order.setPaid(Optional.ofNullable(paymentInfo).map(OrderPaymentInfo::amount).orElse(0.));
        boolean isPaid = NumberUtils.gteZero(order.getPaid());
        order.setType(isPaid ? CustomerOrder.TYPE_ORDER: CustomerOrder.TYPE_CO_HOI);
    }

    public List<CustomerOrderDetail> transformOnCreateDetail(CustomerOrder order) {
        List<CustomerOrderDetail> detailList = new ArrayList<>();
        int i = 1;
        for(CustomerOrderDetail detailOrder : details) {
            CustomerOrderDetail detail = new CustomerOrderDetail();
            CopyProperty.CopyIgnoreNull(detailOrder, detail);
            detail.setCustomerOrderId(order.getId());
            detail.setCode(order.getCode().concat("-" + i));
            detail.setCreatedAt(new Date());
            detail.setStatus(detailOrder.getStatus());
            OrderUtils.calDetailPrice(detail);
            detailList.add(detail);
            i++;
        }
        return detailList;
    }
}
