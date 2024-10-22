package vn.flast.entities;

import vn.flast.models.Customer;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class OrderResponse extends CustomerOrder {
    private Customer customer;
    private List<CustomerOrderDetail> details;
}
