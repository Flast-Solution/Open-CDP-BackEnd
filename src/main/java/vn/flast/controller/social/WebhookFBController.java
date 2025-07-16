package vn.flast.controller.social;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.config.ConfigUtil;

@RestController
@RequestMapping("/webhook")
public class WebhookFBController {

    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge) {
        if ("subscribe".equals(mode) && ConfigUtil.VERIFY_TOKEN_FB.equals(token)) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Verification failed");
    }

    // Nhận tin nhắn từ Fanpage
    @PostMapping
    public ResponseEntity<Void> handleMessage(@RequestBody String payload) {
        System.out.println("Webhook payload: " + payload);
        // Xử lý payload (JSON) để lấy senderId và message
        // Gửi qua WebSocket hoặc lưu vào DB
        return ResponseEntity.ok().build();
    }
}

