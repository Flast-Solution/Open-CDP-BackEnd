package vn.flast.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("configUtil")
public class ConfigUtil {

    public static String HOST_URL;

    public static String VERIFY_TOKEN_FB;

    @Autowired
    public void loadConfig(@Value("${server.host}") String hostUrl,
                           @Value("${verify.token.fb}") String verifyTokenFB) {
        HOST_URL = hostUrl;
        VERIFY_TOKEN_FB = verifyTokenFB;
    }

    public static String getRootPath() {
        return System.getProperty("user.dir") + "/";
    }
}
