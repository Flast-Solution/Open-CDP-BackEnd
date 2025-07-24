package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.CustomerActivities;
import java.util.List;

public interface CustomerActivitiesRepository extends JpaRepository<CustomerActivities, Long> {

    @Query("FROM CustomerActivities p WHERE p.customerId =:customerId")
    List<CustomerActivities> findByCustomerId(Long customerId);
}
