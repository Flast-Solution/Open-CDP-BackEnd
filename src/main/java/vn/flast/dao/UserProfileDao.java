package vn.flast.dao;

import vn.flast.models.User;
import vn.flast.models.UserProfile;

import java.util.List;
import java.util.Set;

public interface UserProfileDao {

    List<UserProfile> findAll();
    UserProfile findByType(String type);
    UserProfile findById(int id);
    Set<User> findUsers(String role);
    void updateLinkProfile(List<Integer> listPermissions, int userId);

}
