package vn.flast.user;

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
