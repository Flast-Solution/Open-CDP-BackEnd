package vn.flast.entities.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;

import java.util.Collection;
import java.util.List;

public class CustomerOrderWithoutDetails extends CustomerOrder {
    @Override
    @JsonIgnore
    public Collection<CustomerOrderDetail> getDetails() {
        return null;
    }
}
