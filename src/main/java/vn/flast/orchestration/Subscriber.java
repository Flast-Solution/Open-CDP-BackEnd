package vn.flast.orchestration;

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
