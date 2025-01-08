package vn.flast.dao;

import vn.flast.models.CustomerOrder;

import java.util.List;

public interface CustomerOrderDao {

    public List<CustomerOrder> findByStatusAndCustomer(int status, int customerId);
}
