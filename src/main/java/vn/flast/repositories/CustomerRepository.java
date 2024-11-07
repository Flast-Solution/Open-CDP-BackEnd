package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}