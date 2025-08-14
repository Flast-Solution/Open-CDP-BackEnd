package vn.flast.service.user;
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
import org.springframework.stereotype.Service;
import vn.flast.controller.BaseController;
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
            input.setMemberList(JsonUtils.toJson(input.getListMember()));
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
