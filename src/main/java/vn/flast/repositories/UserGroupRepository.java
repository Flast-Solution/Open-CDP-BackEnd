package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.UserGroup;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {

    boolean existsByLeaderId(Integer userId);

    @Query("FROM UserGroup u WHERE u.status NOT IN (0)")
    List<UserGroup> findAllByStatus();

    @Query("FROM UserGroup u WHERE u.type = :type AND u.id NOT IN (:id)")
    List<UserGroup> findAllByType(Integer type, Integer id);

    @Query("FROM UserGroup u WHERE u.type = 1 AND u.leaderId = :userId AND u.status NOT IN (0)")
    UserGroup findBySaleManager(Integer userId);
}
