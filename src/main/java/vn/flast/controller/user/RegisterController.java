package vn.flast.controller.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.User;
import vn.flast.repositories.UserRepository;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("user")
public class RegisterController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/register")
    public MyResponse<?> authenticateUser(@Valid @RequestBody User user, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Invalid Input .!");
        }
        String passEncoder = passwordEncoder.encode(user.getPassword());
        user.setPassword(passEncoder);
        userRepository.save(user);
        return MyResponse.response(user);
    }
}
