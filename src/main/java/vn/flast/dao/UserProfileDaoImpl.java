package vn.flast.dao;

import org.springframework.stereotype.Repository;
import vn.flast.models.User;
import vn.flast.models.UserProfile;
import vn.flast.utils.EntityQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("userProfileDao")
public class UserProfileDaoImpl extends DaoImpl<Long, UserProfile> implements UserProfileDao {

    public static final String DESIGNER = "thietke";

    @Override
    public UserProfile findByType(String type) {
        return EntityQuery.create(entityManager, UserProfile.class).stringEqualsTo("type", type)
                .uniqueResult();
    }

    @Override
    public UserProfile findById(int id) {
        return EntityQuery.create(entityManager, UserProfile.class).integerEqualsTo("id", id)
                .uniqueResult();
    }

    @Override
    public List<UserProfile> findAll() {
        return EntityQuery.create(entityManager, UserProfile.class).addAscendingOrderBy("type").list();
    }

    @Override
    public Set<User> findUsers(String role) {
        UserProfile userProfile = findByType(role);
        Set<User> listUsers = new HashSet<>();
        return userProfile == null ? listUsers : userProfile.getUsers();
    }

    @Override
    public void updateLinkProfile(List<Integer> listPermissions, int userId) {
        if(listPermissions.isEmpty() || userId == 0) {
            return;
        }
        entityManager.createNativeQuery("delete from `user_link_profile` where `user_id` =:uId").setParameter("uId", userId).executeUpdate();
        entityManager.flush();
        listPermissions.forEach(item -> {
            String strQ = "insert into `user_link_profile` (`user_id`, `user_profile_id`) values (%s, %s)".formatted(userId, item);
            entityManager.createNativeQuery(strQ).executeUpdate();
        });
    }
}
