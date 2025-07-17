package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.flast.models.Data;

import java.util.List;
import java.util.Optional;

public interface DataRepository extends JpaRepository<Data, Long> {
    @Query(value = "SELECT * FROM data WHERE customer_mobile = :phone ORDER BY in_time DESC LIMIT 1", nativeQuery = true)
    Optional<Data> findFirstByPhone(@Param("phone") String phone);

    @Query("FROM Data d WHERE (:ids IS NULL OR d.id IN (:ids))")
    List<Data> fetchDataIds(@Param("ids") List<Long> ids);

    Boolean existsByCustomerMobile(String phone);
}