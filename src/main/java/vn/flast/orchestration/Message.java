package vn.flast.orchestration;
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




import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
public class Message implements MessageInterface {
    @Setter
    private String topic;

    @Setter
    private Object payload;

    public static Message create(String topic, Object payload) {
        return new Message(topic, payload);
    }

    public Message(String topic, Object payload) {
        this.topic = topic;
        this.payload = payload;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public Object getPayload() {
        return payload;
    }

    public boolean isOrderChange() {
        return Objects.nonNull(topic) && EventTopic.ORDER_CHANGE.equals(topic);
    }
}
