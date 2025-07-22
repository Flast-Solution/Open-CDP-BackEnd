package vn.flast.entities.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class OrderStatus {

    private Integer status;
    private List<Long> orderIds;

    public OrderStatus(Integer status, String orderIdsJson) {
        this.status = status;
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (orderIdsJson != null && !orderIdsJson.isEmpty() && !orderIdsJson.equals("null")) {
                this.orderIds = Arrays.asList(mapper.readValue(orderIdsJson, Long[].class));
            } else {
                this.orderIds = new ArrayList<>();
            }
        } catch (Exception e) {
            this.orderIds = new ArrayList<>();
        }
    }
}
