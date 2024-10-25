package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.CustomerOrderStatus;

public interface CustomerOrderStatusRepository extends JpaRepository<CustomerOrderStatus, Integer> {
}