package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.CustomerPersonal;

public interface CustomerPersonalRepository extends JpaRepository<CustomerPersonal, Long> {

    @Query("FROM CustomerPersonal c WHERE c.mobile = :phone")
    CustomerPersonal findByPhone(String phone);

}