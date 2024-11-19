package vn.flast.orchestration;

import java.util.List;

public class EventTopic {
    public static String DATA_CHANGE = "DATA_CHANGE";
    public static String ORDER_CHANGE = "ORDER_CHANGE";

    public static List<String> allTopic() {
        return List.of(DATA_CHANGE, ORDER_CHANGE);
    }
}
