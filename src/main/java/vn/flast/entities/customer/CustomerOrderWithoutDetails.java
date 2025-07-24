package vn.flast.entities.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;

import java.util.ArrayList;
import java.util.Collection;

public class CustomerOrderWithoutDetails extends CustomerOrder {
    @Override
    @JsonIgnore
    public Collection<CustomerOrderDetail> getDetails() {
        return new ArrayList<>();
    }
}
