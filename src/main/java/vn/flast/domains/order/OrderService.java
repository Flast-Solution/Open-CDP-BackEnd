package vn.flast.domains.order;

import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.entities.OrderResponse;
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
import vn.flast.repositories.CustomerRepository;
import vn.flast.repositories.DataRepository;
import vn.flast.searchs.OrderFilter;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.NumberUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("orderService")
public class OrderService  implements Publisher, Serializable {

    private EventDelegate eventDelegate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private CustomerOrderDetailRepository detailRepository;

    public Ipage<?> fetchList(OrderFilter filter) {
        Integer page = filter.page();
        var et = EntityQuery.create(entityManager, CustomerOrder.class);
        et.like("customerName", filter.customerName())
            .integerEqualsTo("customerId", filter.customerId())
            .integerEqualsTo("companyId", filter.companyId())
            .stringEqualsTo("customerMobile", filter.customerPhone())
            .stringNotEquals("customerEmail", filter.customerEmail())
            .stringEqualsTo("code", filter.code())
            .addDescendingOrderBy("createdAt")
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
    public CustomerOrder save(OrderInput input) {
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
            var data = dataRepository.findFirstByPhone(order.getCustomerMobilePhone()).orElseThrow(
                () -> new ResourceNotFoundException("Not Found Source Data .!")
            );
            order.setDataId(data.getId());
            order.setSource(data.getSource());
            orderRepository.save(order);
        }

        var listDetails = input.transformOnCreateDetail(order);
        order.setDetails(listDetails);
        detailRepository.saveAll(listDetails);

        OrderUtils.calculatorPrice(order);
        this.sendMessageOnOrderChange(order);
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
}
