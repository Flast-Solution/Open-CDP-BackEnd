package vn.flast.utils;

import java.util.Objects;

public class StringUtils {

    public static boolean isNull(String data) {
        return Objects.nonNull(data) && !data.isEmpty();
    }

    public static boolean isNotNull(String data) {
        return !isNull(data);
    }
}
