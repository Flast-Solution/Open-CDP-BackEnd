package vn.flast.repositories;

import vn.flast.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("FROM Customer c WHERE c.mobile =:mobile")
    Optional<Customer> findByMobile(String mobile);
}
