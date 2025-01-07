package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.DataCare;

public interface DataCareRepository extends JpaRepository<DataCare, Integer> {
}
