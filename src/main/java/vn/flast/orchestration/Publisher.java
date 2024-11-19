package vn.flast.orchestration;

public interface Publisher {
    void setDelegate(EventDelegate eventDelegate);
    void publish(MessageInterface message);
}
