package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {

    boolean existsByLeaderId(Integer userId);
}
