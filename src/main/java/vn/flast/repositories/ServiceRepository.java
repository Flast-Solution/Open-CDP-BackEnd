package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.Service;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

    Boolean existsByName(String name);

    @Query("FROM Service s WHERE s.status = 1")
    List<Service> findServiceOn();
}
