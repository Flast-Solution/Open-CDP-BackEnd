package vn.flast.entities.order;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/




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
