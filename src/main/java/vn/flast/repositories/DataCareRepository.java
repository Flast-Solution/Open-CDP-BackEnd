package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.DataCare;

import java.util.List;

public interface DataCareRepository extends JpaRepository<DataCare, Integer> {

    @Query("FROM DataCare d WHERE d.customerId = :customerId")
    List<DataCare> findByCustomerId(Integer customerId);
}
