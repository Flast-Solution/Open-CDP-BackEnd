package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.StatusOrder;

import java.util.List;

public interface StatusOrderRepository extends JpaRepository<StatusOrder, Integer> {

    @Query("FROM StatusOrder s WHERE s.status = 1")
    List<StatusOrder> findAll();

    Boolean existsByName(String name);

    @Query("FROM StatusOrder s WHERE s.name = 'Đơn mới'")
    StatusOrder findStartOrder();

    @Query("FROM StatusOrder s WHERE s.name = 'Hủy đơn'")
    StatusOrder findCancelOrder();
}
