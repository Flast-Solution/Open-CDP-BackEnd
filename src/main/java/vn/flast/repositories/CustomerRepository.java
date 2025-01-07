package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("FROM Customer c WHERE c.mobile = :phone")
    Customer findByPhone(String phone);
}
