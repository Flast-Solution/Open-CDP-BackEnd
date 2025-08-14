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




import vn.flast.searchs.UserFilter;
import vn.flast.models.User;
import vn.flast.pagination.Ipage;
import vn.flast.utils.EntityQuery;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MyUserService")
public class MyUserService {

    @Autowired
    private EntityManager entityManager;

    public Ipage<?> list(UserFilter filter) {
        int LIMIT = 10;
        var et = EntityQuery.create(entityManager, User.class);
        et.stringEqualsTo("ssoId", filter.ssoId())
            .like("fullName", filter.fullName())
            .integerEqualsTo("id", filter.id())
            .setMaxResults(LIMIT)
            .setFirstResult(LIMIT * filter.page());
        return et.toPage();
    }

    public List<User> getUsersByRole(String rule) {
        String query = "select user.* from user join user_link_profile as ulp on user.id = ulp.user_id "  +
                "join user_profile as up on ulp.user_profile_id = up.id " +
                "where up.type=:rule and user.status = 1";
        var users = (List<User>) entityManager.createNativeQuery(query, User.class)
                .setParameter("rule", rule)
                .getResultList();
        return users.stream().map(User::clone).toList();
    }
}
