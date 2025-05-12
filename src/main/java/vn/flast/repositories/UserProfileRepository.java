package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
