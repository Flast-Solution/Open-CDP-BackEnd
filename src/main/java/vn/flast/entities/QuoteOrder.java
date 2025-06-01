package vn.flast.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.flast.models.Config;
import vn.flast.models.CustomerOrder;
import vn.flast.models.User;

@Getter
@Setter
@NoArgsConstructor
public class QuoteOrder {
    private CustomerOrder order;
    private User sale;
    private Config infoBank;
}
