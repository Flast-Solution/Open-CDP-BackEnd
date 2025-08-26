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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.models.UserGroup;
import vn.flast.repositories.UserGroupRepository;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.JsonUtils;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final UserService userService;

    public UserGroup save(UserGroup input) {

        var model = Optional.of(input)
            .map(UserGroup::getId)
            .map(userGroupRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .orElseGet(UserGroup::new);

        var userAdmin = userService.findById(Common.getUserId());
        boolean isPermit = userService.isAdmin(userAdmin.getId())
            || userService.isSaleManager(userAdmin.getId());
        if(!isPermit) {
            throw new SecurityException("Người dùng không có quyền cập nhật group.");
        }

        CopyProperty.CopyIgnoreNull(input, model);
        model.setMemberList(JsonUtils.toJson(input.getListMember()));
        model.setMemberNumber(model.getListMember().size());

        var leader = userService.findById(model.getLeaderId());
        model.setLeaderName(leader.getSsoId());
        return userGroupRepository.save(model);
    }

    public List<UserGroup> fetchGroup() {
        var userAdmin = userService.findById(Common.getUserId());
        boolean isPermit = userService.isAdmin(userAdmin.getId())
            || userService.isSaleManager(userAdmin.getId());
        if(!isPermit) {
            throw new SecurityException("Người dùng không có quyền cập nhật group.");
        }
        var userGroups = userGroupRepository.findAllByStatus();
        for(UserGroup userGroup : userGroups){
            userGroup.setListMember(JsonUtils.Json2ListObject(userGroup.getMemberList(), Integer.class));
        }
        return userGroups;
    }
}
