package vn.flast.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("configUtil")
public class ConfigUtil {

    public static String HOST_URL;
    public static String VERIFY_TOKEN_FB;

    public static String FB_APP_ID;
    public static String FB_APP_SECRET;
    public static String FB_REDIRECT_URL;

    @Autowired
    public void loadConfig(
        @Value("${server.host}") String hostUrl,
        @Value("${fb.app.token}") String verifyTokenFB,
        @Value("${fb.app.id}") String fbAppId,
        @Value("${fb.app.secret}") String fbAppSecret,
        @Value("${fb.app.redirect}") String fbRedirectUri
    ) {
        HOST_URL = hostUrl;
        FB_APP_ID = fbAppId;
        FB_APP_SECRET = fbAppSecret;
        FB_REDIRECT_URL = fbRedirectUri;
        VERIFY_TOKEN_FB = verifyTokenFB;
    }

    public static String getRootPath() {
        return System.getProperty("user.dir") + "/";
    }
}
