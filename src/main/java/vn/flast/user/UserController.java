package vn.flast.user;

import vn.flast.dtos.UserFilter;
import vn.flast.entities.MyResponse;
import vn.flast.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyUserService myUserService;

    @GetMapping("/fetch-all")
    public MyResponse<?> fetchAll() {
        var users = userRepository.findAll();
        return MyResponse.response(users);
    }

    @GetMapping("/list")
    public MyResponse<?> list(UserFilter filter) {
        var iPage = myUserService.list(filter);
        return MyResponse.response(iPage);
    }
}
