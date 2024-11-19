package vn.flast.orchestration;

public interface EventDelegate {
    void addEvent(Subscriber subscriber, String topic);
    void sendEvent(MessageInterface message);
}
