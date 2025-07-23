package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.CustomerOrderStatus;

import java.util.Optional;

public interface CustomerOrderStatusRepository extends JpaRepository<CustomerOrderStatus, Integer> {

    @Query("FROM CustomerOrderStatus s WHERE s.name = 'Đơn mới'")
    CustomerOrderStatus findStartOrder();

    @Query("FROM CustomerOrderStatus s WHERE s.name = 'Hủy đơn'")
    Optional<CustomerOrderStatus> findCancelOrder();
}
