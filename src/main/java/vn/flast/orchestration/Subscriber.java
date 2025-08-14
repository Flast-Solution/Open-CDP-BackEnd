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




import java.util.ArrayList;
import java.util.List;

public abstract class Subscriber {
    /* store all messages received by the subscriber */
    protected List<MessageInterface> subscriberMessages = new ArrayList<>();

    public List<MessageInterface> getSubscriberMessages() {
        return subscriberMessages;
    }
    public void setSubscriberMessages(List<MessageInterface> subscriberMessages) {
        this.subscriberMessages = subscriberMessages;
    }

    /* Add subscriber with PubSubService for a topic */
    public abstract void addSubscriber(String topic, PubSubService pubSubService);

    /* Unsubscribe subscriber with PubSubService for a topic */
    public abstract void unSubscribe(String topic, PubSubService pubSubService);

    public abstract void executeMessage();
}
