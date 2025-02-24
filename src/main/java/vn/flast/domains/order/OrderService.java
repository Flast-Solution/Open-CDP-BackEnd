package vn.flast.domains.order;

import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.controller.common.BaseController;
import vn.flast.entities.OrderResponse;
import vn.flast.entities.OrderStatus;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.CustomerOrder;
import vn.flast.orchestration.EventDelegate;
import vn.flast.orchestration.EventTopic;
import vn.flast.orchestration.Message;
import vn.flast.orchestration.MessageInterface;
import vn.flast.orchestration.Publisher;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.CustomerOrderDetailRepository;
import vn.flast.repositories.CustomerOrderRepository;
import vn.flast.repositories.CustomerPersonalRepository;
import vn.flast.repositories.DataRepository;
import vn.flast.repositories.StatusOrderRepository;
import vn.flast.searchs.OrderFilter;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.NumberUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Service("orderService")
public class OrderService  implements Publisher, Serializable {

    private EventDelegate eventDelegate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private CustomerPersonalRepository customerRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private CustomerOrderDetailRepository detailRepository;

    @Autowired
    private BaseController baseController;

    @Autowired
    private StatusOrderRepository statusOrderRepository;

    public Ipage<?>fetchList(OrderFilter filter) {
        var sale = baseController.getInfo();
        Integer page = filter.page();
        var et = EntityQuery.create(entityManager, CustomerOrder.class);
        if (filter.saleId() != null) {
            boolean isAdminOrManager = sale.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")
                            || auth.getAuthority().equals("ROLE_SALE_MANAGER"));
            et.integerEqualsTo("userCreateId", isAdminOrManager ? filter.saleId() : sale.getId());
        } else {
            et.integerEqualsTo("userCreateId", sale.getId());
        }
        et.like("customerName", filter.customerName())
            .integerEqualsTo("customerId", filter.customerId())
            .stringEqualsTo("customerMobile", filter.customerPhone())
            .stringNotEquals("customerEmail", filter.customerEmail())
            .stringEqualsTo("code", filter.code())
            .addDescendingOrderBy("createdAt")
            .stringEqualsTo("type", filter.type())
            .setMaxResults(filter.limit())
            .setFirstResult(page * filter.limit());

        var lists = transformDetails(et.list());
        return Ipage.generator(filter.limit(), et.count(), page, lists);
    }

    public List<CustomerOrder> transformDetails(List<CustomerOrder> orders) {
        if(Common.CollectionIsEmpty(orders)) {
            return new ArrayList<>();
        }
        var newOrders = orders.stream().map(CustomerOrder::cloneNoDetail).toList();
        var orderIds = newOrders.stream().map(CustomerOrder::getId).toList();
        var details = detailRepository.fetchDetailOrdersId(orderIds);
        for( CustomerOrder order : newOrders ) {
            var detailOfOrder = details.stream().filter(
                d -> d.getCustomerOrderId().equals(order.getId())
            );
            order.setDetails(detailOfOrder.toList());
        }
        return newOrders;
    }

    @Transactional(rollbackFor = Exception.class)
    public CustomerOrder create(OrderInput input) {
        var order = new CustomerOrder();
        order.setCode(OrderUtils.createOrderCode());
        order.setUserCreateUsername(Common.getSsoId());
        order.setUserCreateId(Common.getUserId());
        input.transformOrder(order);
        if(NumberUtils.isNotNull(order.getId())) {
            var entity = orderRepository.findById(order.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Not Found Order .!")
            );
            CopyProperty.CopyIgnoreNull(entity, order);
        } else {
            var data = dataRepository.findFirstByPhone(input.customer().getMobile()).orElseThrow(
                    () -> new RuntimeException("This phone number does not exist in the system.")
            );
            order.setDataId(data.getId());
            order.setSource(data.getSource());
        }
        if(order.getType().equals(CustomerOrder.TYPE_ORDER)){
            order.setStatus(statusOrderRepository.findStartOrder().getId());
        }
        var listDetails = input.transformOnCreateDetail(order);
        order.setDetails(listDetails);
        detailRepository.saveAll(listDetails);

        OrderUtils.calculatorPrice(order);
        orderRepository.save(order);
        this.sendMessageOnOrderChange(order);
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    public CustomerOrder updateOrder(OrderInput input){
        CustomerOrder orderOld = orderRepository.findById(input.id()).orElseThrow(
                () -> new RuntimeException("error no record exists")
        );
        CustomerOrder order = new CustomerOrder();
        input.transformOrder(order);
        CopyProperty.CopyIgnoreNull(orderOld, order);
        var listDetails = input.transformOnCreateDetail(order);
        order.setDetails(listDetails);
        detailRepository.saveAll(listDetails);
        OrderUtils.calculatorPrice(order);
        return order;
    }

    public void sendMessageOnOrderChange(CustomerOrder order) {
        var message = Message.create(EventTopic.ORDER_CHANGE, order.clone());
        this.publish(message);
    }

    @Transactional
    public OrderResponse view(Long id) {
        var order = orderRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Order not found .!")
        );
        return withOrderDetail(order);
    }

    private OrderResponse withOrderDetail(CustomerOrder order) {
        Hibernate.initialize(order.getDetails());
        var orderRep = new OrderResponse();
        CopyProperty.CopyNormal(order.clone(), orderRep);
        orderRep.setCustomer(customerRepository.findById(orderRep.getCustomerId()).orElse(null));
        return orderRep;
    }

    @Transactional
    public OrderResponse findByCode(String code) {
        var order = orderRepository.findByCode(code).orElseThrow(
            () -> new ResourceNotFoundException("Order not found .!")
        );
        return withOrderDetail(order);
    }

    @Override
    public void setDelegate(EventDelegate eventDelegate) {
        this.eventDelegate = eventDelegate;
    }

    @Override
    public void publish(MessageInterface message) {
        if(Objects.nonNull(eventDelegate)) {
            eventDelegate.sendEvent(message);
        }
    }

    public static String createOrderCode(String customerMobilePhone, String source) {
        if (customerMobilePhone == null) {
            return null;
        }
        String lastThereDigits = customerMobilePhone.substring(customerMobilePhone.length() - 3);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int day = calendar.get(Calendar.DATE);
        /* month +1 the month for current month */
        int month = calendar.get(Calendar.MONTH) + 1;
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String subYear = year.substring(year.length() - 2);
        String endCode = day + month + subYear + lastThereDigits;
        return source
                + Common.getAlphaNumericString(1, false)
                + Common.getAlphaNumericString(2, true)
                + endCode;
    }

    @Transactional
    public void cancelCohoi(Long orderId, Boolean detail){
        Integer statusCancel = statusOrderRepository.findCancelOrder().getId();
        if(detail){
            var order = detailRepository.findById(orderId).orElseThrow(
                    () -> new RuntimeException("error no record exists")
            );
            order.setStatus(statusCancel);
        }
        else {
            var order = orderRepository.findById(orderId).orElseThrow(
                    () -> new RuntimeException("error no record exists")
            );
            order.setStatus(statusCancel);
        }

    }
}
