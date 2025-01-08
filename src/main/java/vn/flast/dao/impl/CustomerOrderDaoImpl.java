package vn.flast.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import vn.flast.dao.CustomerOrderDao;
import vn.flast.domains.order.OrderService;
import vn.flast.models.CustomerOrder;
import vn.flast.utils.EntityQuery;

import java.util.List;

public class CustomerOrderDaoImpl implements CustomerOrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderService orderService;

    @Override
    public List<CustomerOrder> findByStatusAndCustomer(int status, int customerId) {
        var et = EntityQuery.create(entityManager, CustomerOrder.class);
        var orders = et.integerEqualsTo("status", status).integerEqualsTo("customerId", customerId).setMaxResults(3).addDescendingOrderBy("id").list();
        var data = orderService.transformDetails(orders);
        return data;
    }
}
