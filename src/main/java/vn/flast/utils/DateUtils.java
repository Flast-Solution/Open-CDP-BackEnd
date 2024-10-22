package vn.flast.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    public static int numDaysToDate(Date end, Date start) {
        long diff = end.getTime() - start.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Date addDays(Date dt, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String dateToString(Date date, String format) {
        if(date == null) {
            return "N/A";
        }
        /* format: yyyy-MM-dd HH:mm:ss */
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date strToDate(String date, String format) {
        /* format: yyyy-MM-dd HH:mm:ss */
        try {
            if(date == null) {
                return null;
            }
            return new SimpleDateFormat(format == null ? "yyyy-MM-dd HH:mm:ss" : format).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
