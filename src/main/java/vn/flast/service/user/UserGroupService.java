package vn.flast.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.controller.common.BaseController;
import vn.flast.models.UserGroup;
import vn.flast.repositories.UserGroupRepository;

import java.util.List;

@Service
public class UserGroupService extends BaseController {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserService userService;

    public UserGroup createGroup(UserGroup input) {
        var userCreate = getInfo();
        var userAdmin = userService.findById(userCreate.getId());
        if (userService.isAdmin(userAdmin.getId())) {
            if (userGroupRepository.existsByLeaderId(input.getLeaderId())) {
                throw new IllegalArgumentException("Group với leader này đã tồn tại.");
            }
            return userGroupRepository.save(input);
        }
        throw new SecurityException("Người dùng không có quyền tạo group."); // Nếu không phải Admin
    }

    public UserGroup update(UserGroup input){
        var userCreate = getInfo();
        var userAdmin = userService.findById(userCreate.getId());
        if (userService.isAdmin(userAdmin.getId()) || userService.isSaleManager(userAdmin.getId())) {
            if (userGroupRepository.existsByLeaderId(input.getLeaderId())) {
                throw new IllegalArgumentException("Group với leader này đã tồn tại.");
            }
            return userGroupRepository.save(input);
        }
        throw new SecurityException("Người dùng không có quyền cập nhật group."); // Nếu không phải Admin
    }

    public List<UserGroup> fetchGroup(){
//        var userCreate = getInfo();
//        var userAdmin = userService.findById(userCreate.getId());
//        if (userService.isAdmin(userAdmin.getId()) || userService.isSaleManager(userAdmin.getId())) {
            return userGroupRepository.findAllByStatus();
//        }
//        throw new SecurityException("Người dùng không có quyền.");
    }

}
