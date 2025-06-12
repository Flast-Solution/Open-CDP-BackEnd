package vn.flast.entities.order;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import lombok.Getter;
import lombok.Setter;
import vn.flast.models.CustomerPersonal;

import java.util.List;

@Getter @Setter
public class OrderResponse extends CustomerOrder {
    private CustomerPersonal customer;
    private List<CustomerOrderDetail> details;
}
