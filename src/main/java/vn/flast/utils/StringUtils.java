package vn.flast.utils;

public class StringUtils {

    public static boolean isNull(String data) {
        return data == null || data.isEmpty();
    }

    public static boolean isNotNull(String data) {
        return data != null && !data.isEmpty();
    }
}
