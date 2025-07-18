package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.CustomerOrderDetail;
import java.util.List;

public interface CustomerOrderDetailRepository extends JpaRepository<CustomerOrderDetail, Long> {
    @Query("FROM CustomerOrderDetail d WHERE d.customerOrderId IN (:orderId)")
    List<CustomerOrderDetail> fetchDetailOrdersId(List<Long> orderId);

    @Modifying
    @Query("DELETE FROM CustomerOrderDetail d WHERE d.customerOrderId =:orderId")
    void deleteByOrderId(Long orderId);
}
