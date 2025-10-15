package vn.flast.config;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/

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
