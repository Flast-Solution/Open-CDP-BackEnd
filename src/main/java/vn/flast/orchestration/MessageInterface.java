package vn.flast.orchestration;

public interface MessageInterface {
    String getTopic();
    Object getPayload();
    boolean isOrderChange();
}
