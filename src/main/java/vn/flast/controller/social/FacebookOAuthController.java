package vn.flast.controller.social;
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





import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vn.flast.config.ConfigUtil;

import java.util.Map;

@RestController
@RequestMapping("/facebook")
public class FacebookOAuthController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/callback")
    public ResponseEntity<?> handleCallback(@RequestParam("code") String code) {
        String accessTokenUrl = UriComponentsBuilder
                .fromHttpUrl("https://graph.facebook.com/v22.0/oauth/access_token")
                .queryParam("client_id", ConfigUtil.FB_APP_ID)
                .queryParam("redirect_uri", ConfigUtil.FB_REDIRECT_URL)
                .queryParam("client_secret", ConfigUtil.FB_APP_SECRET)
                .queryParam("code", code)
                .toUriString();

        ResponseEntity<Map> response = restTemplate.getForEntity(accessTokenUrl, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String accessToken = (String) response.getBody().get("access_token");
            return ResponseEntity.ok("User Access Token: " + accessToken);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi lấy access token");
        }
    }
}
