package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.CustomerActivities;

public interface CustomerActivitiesRepository extends JpaRepository<CustomerActivities, Long> {
}
