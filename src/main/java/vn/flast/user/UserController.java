package vn.flast.user;
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




import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.flast.dao.UserProfileDao;
import vn.flast.entities.UserInput;
import vn.flast.models.User;
import vn.flast.searchs.UserFilter;
import vn.flast.entities.MyResponse;
import vn.flast.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.service.user.UserService;
import vn.flast.utils.CopyProperty;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileDao userProfileDao;

    @GetMapping("/list")
    public MyResponse<?> list(UserFilter filter) {
        var iPage = myUserService.list(filter);
        return MyResponse.response(iPage);
    }

    @PostMapping(value = "/create")
    public MyResponse<?> registerUser(@RequestBody UserInput data){
        if(userRepository.existsBySsoId(data.getSsoId()) || userRepository.existsByEmail(data.getEmail())){
            return MyResponse.response(404, "Thông tin không hợp lệ");
        }
        try {
            User user = new User();
            CopyProperty.CopyIgnoreNull(data, user);
            String enPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(enPass);
            userRepository.save(user);
            return MyResponse.response(user);
        } catch (RuntimeException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @PostMapping(value = "/update")
    public MyResponse<?> updateUser(@RequestBody User data, @RequestParam Integer id){
        var user = userRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Tài khoản này không tồn tại trong hệ thống")
        );
        CopyProperty.CopyIgnoreNull(data, user);
        if(data.getPassword() != null) {
            String enPass = passwordEncoder.encode(data.getPassword());
            user.setPassword(enPass);
        }
        userRepository.save(user);
        return MyResponse.response(user);
    }

    @GetMapping(value = "/find-id")
    public MyResponse<?> findUser(@RequestParam Integer id) {
        var user = userRepository.findById(id).orElseThrow(
            () -> new RuntimeException("This ID does not exist in the system ")
        );
        return MyResponse.response(user);
    }

    @PostMapping("/uploads-file")
    public MyResponse<?> multiFileUpload(
        @RequestParam(value = "file") MultipartFile multipartFile
    ) throws Exception {
        if (multipartFile.isEmpty()) {
            throw new RuntimeException("File not Empty");
        }
        var response = userService.uploadFile(multipartFile);
        return MyResponse.response(response);
    }

    @GetMapping("/list-sale")
    public MyResponse<?> listSale(){
        var data = userService.findBySale();
        return MyResponse.response(data);
    }

    @GetMapping("/list-role")
    public MyResponse<?> listRole(){
        var data = userProfileDao.findAll();
        return MyResponse.response(data);
    }

    @GetMapping("/list-name-id")
    public MyResponse<?> listNameId(@RequestParam(name = "ids", defaultValue = "") List<Integer> ids) {
        List<User> users;
        if(ids.isEmpty()) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findAllById(ids);
        }
        var data = users.stream().map(
        user -> Map.of("id", user.getId(), "name", user.getSsoId())
        );
        return MyResponse.response(data.toList());
    }
}
