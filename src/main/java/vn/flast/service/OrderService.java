package vn.flast.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.entities.OrderResponse;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.models.CustomerOrder;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.CustomerOrderDetailRepository;
import vn.flast.repositories.CustomerOrderRepository;
import vn.flast.repositories.CustomerRepository;
import vn.flast.searchs.OrderFilter;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerOrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

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
            order.setOrderDetails(detailOfOrder.toList());
        }
        return newOrders;
    }

    @Transactional(rollbackOn = { Exception.class })
    public CustomerOrder create(CustomerOrder order) {
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public OrderResponse view(Long id) {
        var order = orderRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Order not found .!")
        );
        return withOrderDetail(order);
    }

    private OrderResponse withOrderDetail(CustomerOrder order) {
        Hibernate.initialize(order.getOrderDetails());
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
}
