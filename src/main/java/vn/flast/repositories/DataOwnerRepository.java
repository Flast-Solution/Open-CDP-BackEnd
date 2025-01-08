package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.DataOwner;

public interface DataOwnerRepository extends JpaRepository<DataOwner, Long> {

    @Query("FROM DataOwner d WHERE d.customerMobile = :phone")
    DataOwner findByMobile(String phone);
}