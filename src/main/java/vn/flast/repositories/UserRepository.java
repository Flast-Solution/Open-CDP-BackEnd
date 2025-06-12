package vn.flast.repositories;


import vn.flast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "FROM User u WHERE u.email=:email")
    User findByEmail(String email);

    @Query(value = "FROM User u WHERE u.status = 1")
    List<User> findAll();
    @Query(value = "FROM User u WHERE u.ssoId = :ssoId")
    User findBySsoId(String ssoId);

    Boolean existsBySsoId(String ssoId);

    Boolean existsByEmail(String email);

}