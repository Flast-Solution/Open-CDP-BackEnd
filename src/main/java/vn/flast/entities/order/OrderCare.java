package vn.flast.entities.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderCare {

    private String orderCode;
    private String note;
    private String userName;
    private String userNote;
    private Integer type;
    private Integer cause;

}
