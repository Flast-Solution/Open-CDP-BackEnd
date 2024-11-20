package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.CustomerOrderPayment;

public interface CustomerOrderPaymentRepository extends JpaRepository<CustomerOrderPayment, Long> {
}