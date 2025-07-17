package vn.flast.domains.order;

import vn.flast.domains.payments.OrderPaymentInfo;
import vn.flast.entities.order.OrderDetail;
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
import java.util.Objects;

public record OrderInput(
    Long id,
    CustomerPersonal customer,
    OrderDiscount discount,
    OrderPaymentInfo paymentInfo,
    Integer vat,
    Integer shipping,
    List<OrderDetail> details,
    String note,
    String address
) {

    public void transformOrder(CustomerOrder order) {
        if(Common.CollectionIsEmpty(details)) {
            throw new RuntimeException("Order detail is required .!");
        }
        if(Objects.isNull(customer)) {
            throw new RuntimeException("Customer is required .!");
        }
        order.setId(id);
        order.setCustomerId(customer.getId());
        order.setCustomerReceiverName(customer.getName());
        order.setCustomerEmail(customer.getEmail());
        order.setCustomerMobilePhone(customer.getMobile());
        order.setDiscountInfo(JsonUtils.toJson(discount));
        order.setCustomerNote(note);
        order.setCustomerAddress(address);
        order.setShippingCost(shipping);
        order.setOpportunityAt(new Date());
        if(NumberUtils.isNotNull(vat)) {
            order.setVat(vat);
        }
        boolean isPaid = NumberUtils.gteZero(order.getPaid());
        order.setType(isPaid ? CustomerOrder.TYPE_ORDER: CustomerOrder.TYPE_CO_HOI);
    }

    public List<CustomerOrderDetail> transformOrderDetail(CustomerOrder order, int status) {
        List<CustomerOrderDetail> detailList = new ArrayList<>();
        int i = 1;
        for(OrderDetail detailInput : details) {
            CustomerOrderDetail detail = createOrderDetailFromInput(detailInput);
            detail.setCustomerOrderId(order.getId());
            if(Objects.nonNull(detail.getCode())){
                detail.setCode(order.getCode().concat("-" + i));
                detail.setCreatedAt(new Date());
            }
            if(order.getType().equals(CustomerOrder.TYPE_ORDER)){
                detail.setStatus(status);
            }
            OrderUtils.calDetailPrice(detail, detailInput);
            detailList.add(detail);
            i++;
        }
        return detailList;
    }

    public CustomerOrderDetail createOrderDetailFromInput(OrderDetail detailInput) {
        CustomerOrderDetail detail = new CustomerOrderDetail();
        CopyProperty.CopyIgnoreNull(detailInput, detail, "skuInfo");
        detail.setSkuInfo(JsonUtils.toJson(detailInput.getSkuDetails()));
        detail.setName(detailInput.getOrderName());
        detail.setTotal(detailInput.getTotalPrice());
        detail.setPriceOff(detailInput.getDiscountAmount());
        detail.setSkuId(Long.valueOf(detailInput.getSkuDetailCode()));
        return detail;
    }
}
