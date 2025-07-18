package vn.flast.components;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.flast.controller.BaseController;
import vn.flast.models.User;
import vn.flast.service.user.UserService;
import vn.flast.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
public class GetUserRole extends BaseController {

    @Autowired
    private UserService userService;

    public List<Integer> listUserIds() {
        var ids = new ArrayList<Integer>();
        User user = userService.findById(getInfo().getId());
        if(user.checkRule(User.RULE_ADMIN)) {
            return ids;
        }
        if(user.checkRule(User.RULE_SALE_LEADER)) {
            var leaderGroups = userService.findByLeaderId(user.getId());
            Validate.notNull(leaderGroups, "Không tìm thấy sale leader id trong userGroup");
            return JsonUtils.Json2ListObject(leaderGroups.getMemberList(), Integer.class);
        }
        ids.add(user.getId());
        return ids;
    }

    public List<Integer> listUserIdsByleader(Integer leaderId) {
        var leaderGroups = userService.findByLeaderId(leaderId);
        Validate.notNull(leaderGroups, "Không tìm thấy sale leader id trong userGroup");
        List<Integer> ids = Optional.ofNullable(leaderGroups.getMemberList())
                .map(json -> JsonUtils.Json2ListObject(json, Integer.class))
                .orElse(new ArrayList<>());
        ids.add(leaderId);
        return ids;
    }
}
