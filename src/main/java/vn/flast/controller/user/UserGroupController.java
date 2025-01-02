package vn.flast.controller.user;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.models.UserGroup;
import vn.flast.models.WareHouseHistory;
import vn.flast.service.user.UserGroupService;
import vn.flast.validator.ValidationErrorBuilder;

@RestController
@RequestMapping("/user-group")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @PostMapping("/created")
    public MyResponse<?> created(@Valid @RequestBody UserGroup input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        var data = userGroupService.createGroup(input);
        return MyResponse.response(data, "Tạo mới team thành công .!");
    }

    @PostMapping("/update")
    public MyResponse<?> update(@Valid @RequestBody UserGroup input, Errors errors) {
        if(errors.hasErrors()) {
            var newErrors = ValidationErrorBuilder.fromBindingErrors(errors);
            return MyResponse.response(newErrors, "Lỗi tham số đầu vào");
        }
        var data = userGroupService.update(input);
        return MyResponse.response(data, "update thông tin team thành công .!");
    }

    @GetMapping("/fetch")
    public MyResponse<?> fetchGroup() {
        var data = userGroupService.fetchGroup();
        return MyResponse.response(data, "update thông tin team thành công .!");
    }
}
