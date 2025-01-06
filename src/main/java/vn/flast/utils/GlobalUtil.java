package vn.flast.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Log4j2
public class GlobalUtil {

    private static final int VALUE_NOT_CONVERT = 0;

    public static int getFolderUpload(Integer objectId) {
        String _dateStr = new SimpleDateFormat("MM-01-yyyy").format(new Date(objectId * 1000L));
        try {
            Date _date = new SimpleDateFormat("MM-01-yyyy").parse(_dateStr);
            return (int) Math.ceil((double) dateToInt(_date)/2048);
        } catch (ParseException e) {
            log.info("ParseException Date : {}", e.getMessage());
            return VALUE_NOT_CONVERT;
        }
    }

    public static int dateToInt() {
        return (int) (new Date().getTime()/1000);
    }

    public static int dateToInt(Date _d) {
        return (int) (_d.getTime()/1000);
    }

    public static String setFileName(String name) throws NoSuchAlgorithmException {
        String nameUnique = name + dateToInt();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(nameUnique.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        byte[] arrayOfByte1;
        int j = (arrayOfByte1 = hashInBytes).length;
        for (int i = 0; i < j; i++) {
            byte b = arrayOfByte1[i];
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String pathNameFile(String name) {
        String[] fileFrags = name.split("\\.");
        try {
            return fileFrags[fileFrags.length - 1];
        } catch (Exception e) {
            return "txt";
        }
    }
}
