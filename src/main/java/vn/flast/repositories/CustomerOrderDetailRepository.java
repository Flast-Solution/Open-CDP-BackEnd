package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.CustomerOrderDetail;

public interface CustomerOrderDetailRepository extends JpaRepository<CustomerOrderDetail, Long> {
}