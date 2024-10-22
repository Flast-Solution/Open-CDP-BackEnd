package vn.flast.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("configUtil")
public class ConfigUtil {

    public static String HOST_URL;
    public static Integer VAT_PERCENT;
    public static String STATUS_ACTIVE = "1";
    public static String STATUS_NOT_ACTIVE = "2";

    public static String ORDER_STATUS_UN_CONFIRM = "UNCONFIRM";
    public static String ORDER_STATUS_CONFIRM = "CONFIRM";
    public static String ORDER_STATUS_PROGRESS = "INPROGRESS";
    public static String ORDER_STATUS_COMPLETED = "COMPLETED";
    public static String ORDER_STATUS_CANCELLED = "CANCELLED";

    public static String PAYMENT_STATUS_PAID = "PAID";
    public static String PAYMENT_STATUS_UNPAID = "UNPAID";

    @Autowired
    public void loadConfig(
        @Value("${server.host}") String hostUrl,
        @Value("${config.vat}") Integer vat
    ) {
        HOST_URL = hostUrl;
        VAT_PERCENT = vat;
    }

    public static String getRootPath() {
        return System.getProperty("user.dir") + "/";
    }
}
