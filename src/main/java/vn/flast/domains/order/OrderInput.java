package vn.flast.domains.order;

import lombok.With;
import vn.flast.domains.payments.OrderPaymentInfo;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import vn.flast.models.CustomerPersonal;
import vn.flast.models.DetailItem;
import vn.flast.repositories.CustomerOrderStatusRepository;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DetailItemRepository;
import vn.flast.repositories.ProductRepository;
import vn.flast.utils.BeanUtil;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.JsonUtils;
import vn.flast.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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


    public List<CustomerOrderDetail> transformOnCreateDetail(CustomerOrder order) {
        List<CustomerOrderDetail> detailList = new ArrayList<>();
        int i = 1;
        for(CustomerOrderDetail detailOrder : details) {
            CustomerOrderDetail detail = new CustomerOrderDetail();
            CopyProperty.CopyIgnoreNull(detailOrder, detail);
            detail.setCustomerOrderId(order.getId());
            if(detail.getCode() == null){
                detail.setCode(order.getCode().concat("-" + i));
                detail.setCreatedAt(new Date());
            }
            if(order.getType().equals(CustomerOrder.TYPE_ORDER)){
                var statusRepo =  BeanUtil.getBean(CustomerOrderStatusRepository.class);
                detail.setStatus(statusRepo.findStartOrder().getId());
            }
            OrderUtils.calDetailPrice(detail);
            detailList.add(detail);
            i++;
        }
        return detailList;
    }

    public List<DetailItem> transformOnCreateDetailItem(List<CustomerOrderDetail> orderDetails) {
        if (orderDetails == null || orderDetails.isEmpty()) {
            return Collections.emptyList();
        }
        List<DetailItem> detailItemList = new ArrayList<>();
        var itemRepo = BeanUtil.getBean(DetailItemRepository.class);
        List<DetailItem> itemsToUpdate = new ArrayList<>();

        for (CustomerOrderDetail orderDetail : orderDetails) {
            if (orderDetail.getItems() == null || orderDetail.getItems().isEmpty()) {
                continue;
            }
            List<DetailItem> itemsOld = itemRepo.findByDetailId(orderDetail.getId());
            Set<Long> newItemIds = orderDetail.getItems().stream()
                    .map(DetailItem::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            for (DetailItem oldItem : itemsOld) {
                if (!newItemIds.contains(oldItem.getId())) {
                    oldItem.setStatus(0);
                    itemsToUpdate.add(oldItem);
                }
            }
            for (DetailItem item : orderDetail.getItems()) {
                DetailItem newItem = new DetailItem();
                CopyProperty.CopyIgnoreNull(item, newItem);
                if(newItem.getName() == null) {
                    var productRepo =  BeanUtil.getBean(ProductRepository.class);
                    newItem.setName(productRepo.findNameBySkuId(newItem.getSkuId()));
                }
                newItem.setOrderDetailId(orderDetail.getId());
                newItem.setCreatedAt(new Date());
                detailItemList.add(newItem);
            }
        }
        if (!itemsToUpdate.isEmpty()) {
            itemRepo.saveAll(itemsToUpdate);
        }
        return detailItemList;
    }
}
