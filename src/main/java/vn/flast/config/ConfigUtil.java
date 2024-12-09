package vn.flast.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("configUtil")
public class ConfigUtil {

    public static String HOST_URL;

    @Autowired
    public void loadConfig(@Value("${server.host}") String hostUrl) {
        HOST_URL = hostUrl;
    }

    public static String getRootPath() {
        return System.getProperty("user.dir") + "/";
    }
}
