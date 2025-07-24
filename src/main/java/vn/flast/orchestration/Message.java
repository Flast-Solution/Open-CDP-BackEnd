package vn.flast.orchestration;

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
