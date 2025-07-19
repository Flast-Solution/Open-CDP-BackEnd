package vn.flast.utils;

public class NumberUtils {

    public static boolean isNull(Integer num) {
        return null == num;
    }

    public static boolean isNull(Double num) {
        return null == num;
    }

    public static boolean isNull(Long num) {
        return null == num;
    }

    public static boolean isNotNull(Integer num) {
        return !isNull(num);
    }

    public static boolean isNotNull(Long num) {
        return !isNull(num);
    }

    public static boolean gteZero(Double num) {
        return !isNull(num) && num > 0;
    }

    public static boolean gteZero(Integer num) {
        return isNotNull(num) && num > 0;
    }

    public static double calculatorPercent(double fee, double percent) {
        return (fee/100)*percent;
    }
}
