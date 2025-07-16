package vn.flast.domains.order;

import lombok.With;
import vn.flast.domains.payments.OrderPaymentInfo;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import vn.flast.models.CustomerPersonal;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.utils.BeanUtil;
import vn.flast.utils.Common;
import vn.flast.utils.JsonUtils;
import vn.flast.utils.NumberUtils;

import java.util.Date;
import java.util.List;

public record OrderInput(
    Long id,
    @With CustomerPersonal customer,
    OrderDiscount discount,
    @With OrderPaymentInfo paymentInfo,
    Integer vat,
    Integer shipping,
    List<CustomerOrderDetail> details,
    String note,
    String address,
    Long dataId
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
        order.setCustomerAddress(address);
        order.setShippingCost(shipping);
        order.setOpportunityAt(new Date());
        if(NumberUtils.isNotNull(vat)) {
            order.setVat(vat);
        }
        boolean isPaid = NumberUtils.gteZero(order.getPaid());
        order.setType(isPaid ? CustomerOrder.TYPE_ORDER: CustomerOrder.TYPE_CO_HOI);
    }

    public CustomerPersonal transformCustomer(CustomerPersonal customer) {
        if (customer.getMobile() == null) {
            throw new RuntimeException("Phone number cannot be left blank!");
        }
        var customerRepo = BeanUtil.getBean(CustomerPersonalRepository.class);
        var customerOld = customerRepo.findByPhone(customer.getMobile());
        return (customerOld != null) ? customerOld : customerRepo.save(customer);
    }
}
