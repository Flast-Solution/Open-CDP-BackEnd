package vn.flast.user;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.jwt.JwtProvider;
import vn.flast.models.User;
import vn.flast.repositories.UserRepository;
import vn.flast.security.UserPrinciple;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyUserDetailsService userService;

    @PostMapping(value = "/sign-in")
    public MyResponse<?> authenticateUser(@RequestBody LoginForm loginForm) {
        String passEncoder = passwordEncoder.encode(loginForm.password);
        logger.info(loginForm.username + "|" + loginForm.password + "|" + passEncoder);
        User user = Optional.ofNullable(userRepository.findBySsoId( loginForm.username )).orElseThrow(
            () -> new RuntimeException("User not found !")
        );
        if(!passwordEncoder.matches(loginForm.password, user.getPassword())) {
            throw new RuntimeException("User not found !");
        }
        UserPrinciple userPrinciple = UserPrinciple.build(user);
        String jwt = jwtProvider.generateJwtToken(userPrinciple);

        user.setUserProfiles(userService.findProfile(user.getId()));
        DataUser retUser = new DataUser(user, jwt);
        return MyResponse.response(retUser);
    }

    @PostMapping(value = "/sign-with-token")
    public MyResponse<?> signWithToken(@RequestBody RToken rToken) {
        Claims claims = jwtProvider.getClaims(rToken.token);
        Integer uId = Integer.parseInt((String) claims.get("userId"));
        User user = userRepository.findById(uId).orElseThrow(
            () -> new RuntimeException("user token invalid .!")
        );
        user.setUserProfiles(userService.findProfile(user.getId()));
        DataUser dataUser = new DataUser(user, rToken.token);
        return MyResponse.response(dataUser);
    }

    public record LoginForm(String username, String password) {}
    public record RToken(String token) {}
    public record DataUser(User user, String jwtToken) {}
}
