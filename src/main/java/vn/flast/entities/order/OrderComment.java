package vn.flast.entities.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.models.CustomerOrder;
import vn.flast.models.FlastNote;

@Getter @Setter
@NoArgsConstructor
public class OrderComment extends CustomerOrder {
    private FlastNote notes;
}
