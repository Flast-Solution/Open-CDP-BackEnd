package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.flast.models.Data;

import java.util.List;
import java.util.Optional;

public interface DataRepository extends JpaRepository<Data, Long> {
    @Query("FROM Data d WHERE d.customerMobile = :phone")
    Optional<Data> findFirstByPhone(String phone);

    @Query("FROM Data d WHERE (:ids IS NULL OR d.saleId IN (:ids))")
    List<Data> fetchDataIds(@Param("ids") List<Long> ids);
}