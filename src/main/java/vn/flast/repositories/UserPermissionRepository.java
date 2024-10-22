package vn.flast.repositories;

import vn.flast.models.UserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Long> {
}