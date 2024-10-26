package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.DataWork;

public interface DataWorkRepository extends JpaRepository<DataWork, Long> {
}
