package vn.flast.user;

import vn.flast.dtos.UserFilter;
import vn.flast.models.User;
import vn.flast.pagination.Ipage;
import vn.flast.utils.EntityQuery;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
