package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.CustomerOrderStatus;

import java.util.List;

public interface CustomerOrderStatusRepository extends JpaRepository<CustomerOrderStatus, Integer> {

    @Query("FROM CustomerOrderStatus s WHERE s.status = 1")
    List<CustomerOrderStatus> findAll();

    Boolean existsByName(String name);

    @Query("FROM CustomerOrderStatus s WHERE s.name = 'Đơn mới'")
    CustomerOrderStatus findStartOrder();

    @Query("FROM CustomerOrderStatus s WHERE s.name = 'Hủy đơn'")
    CustomerOrderStatus findCancelOrder();
}