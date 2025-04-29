package vn.flast.entities.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerOrderDetail;
import vn.flast.models.CustomerOrderPayment;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderPayment extends CustomerOrder {
    @Override
    @JsonIgnore
    public Collection<CustomerOrderDetail> getDetails() {
        return null;
    }
    private List<CustomerOrderPayment> payments;
}
