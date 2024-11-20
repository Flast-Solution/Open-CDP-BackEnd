package vn.flast.orchestration;

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
