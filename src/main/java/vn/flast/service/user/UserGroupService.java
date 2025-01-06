package vn.flast.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.controller.common.BaseController;
import vn.flast.models.UserGroup;
import vn.flast.repositories.UserGroupRepository;
import vn.flast.utils.JsonUtils;

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
            input.setMemberNumber(input.getListMember().size());
            return userGroupRepository.save(input);
        }
        throw new SecurityException("Người dùng không có quyền tạo group."); // Nếu không phải Admin
    }

    public UserGroup update(UserGroup input){
        var userCreate = getInfo();
        var userAdmin = userService.findById(userCreate.getId());
        if (userService.isAdmin(userAdmin.getId()) || userService.isSaleManager(userAdmin.getId())) {
            var groupOld = userGroupRepository.findById(input.getId()).orElseThrow(
                    () -> new RuntimeException("no record exists!")
            );
            groupOld.setListMember(JsonUtils.Json2ListObject(groupOld.getMemberList(), Integer.class));
            var groupType = userGroupRepository.findAllByType(input.getType(), input.getId());

            if (groupOld.getListMember() != null) {
                for (UserGroup group : groupType) {
                    group.setListMember(JsonUtils.Json2ListObject(group.getMemberList(), Integer.class));
                    if (group.getListMember() != null) {
                        boolean hasDuplicate = input.getListMember().stream()
                                .anyMatch(id -> group.getListMember().contains(id));
                        if (hasDuplicate) {
                            throw new RuntimeException("Duplicate member ID found!");
                        }
                    }
                }
            }
            input.setMemberList(JsonUtils.toJson(input.getListMember()));
            input.setMemberNumber(input.getListMember().size());

            return userGroupRepository.save(input);
        }
        throw new SecurityException("Người dùng không có quyền cập nhật group."); // Nếu không phải Admin
    }

    public List<UserGroup> fetchGroup(){
        var userCreate = getInfo();
        var userAdmin = userService.findById(userCreate.getId());
        if (userService.isAdmin(userAdmin.getId()) || userService.isSaleManager(userAdmin.getId())) {
             var userGroups = userGroupRepository.findAllByStatus();
             for(UserGroup userGroup : userGroups){
                 userGroup.setListMember(JsonUtils.Json2ListObject(userGroup.getMemberList(), Integer.class));
             }
             return userGroups;
        }
        throw new SecurityException("Người dùng không có quyền.");
    }

}
