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




import vn.flast.utils.Common;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class PubSubService {
    /* Keeps set of subscriber topic wise, using set to prevent duplicates */
    Map<String, Set<Subscriber>> subscribersTopicMap = new HashMap<>();

    /* Holds messages published by publishers */
    Queue<MessageInterface> messagesQueue = new LinkedList<>();

    public void addMessageToQueue(MessageInterface message){
        messagesQueue.add(message);
        this.broadcast();
    }

    public void addSubscriber(String topic, Subscriber subscriber){
        Set<Subscriber> subscribers = subscribersTopicMap.containsKey(topic) ? subscribersTopicMap.get(topic) : new HashSet<>();
        subscribers.add(subscriber);
        subscribersTopicMap.put(topic, subscribers);
    }

    public void removeSubscriber(String topic, Subscriber subscriber){
        if(subscribersTopicMap.containsKey(topic)){
            Set<Subscriber> subscribers = subscribersTopicMap.get(topic);
            subscribers.remove(subscriber);
            subscribersTopicMap.put(topic, subscribers);
        }
    }

    private void broadcast(){
        while(!messagesQueue.isEmpty()){
            MessageInterface message = messagesQueue.remove();
            String topic = message.getTopic();
            Set<Subscriber> subscribersOfTopic = subscribersTopicMap.get(topic);
            if(Common.CollectionIsEmpty(subscribersOfTopic)) {
                continue;
            }
            for(Subscriber subscriber : subscribersOfTopic){
                List<MessageInterface> subscriberMessages = subscriber.getSubscriberMessages();
                subscriberMessages.add(message);
            }
        }
    }
}
