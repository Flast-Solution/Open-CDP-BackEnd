package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.flast.models.CustomerOrderDetail;
import java.util.List;

public interface CustomerOrderDetailRepository extends JpaRepository<CustomerOrderDetail, Long> {
    @Query("FROM CustomerOrderDetail d WHERE d.customerOrderId IN (:orderId)")
    List<CustomerOrderDetail> fetchDetailOrdersId(List<Long> orderId);

    @Modifying
    @Query("UPDATE CustomerOrderDetail u SET u.status = :status WHERE u.id = :detailId")
    void updateDetailStatus(Integer status, Long detailId);

    @Query(value = """
    SELECT status,JSON_ARRAYAGG(customer_order_id) AS orderIds
    FROM (
        SELECT DISTINCT customer_order_id, status, ROW_NUMBER() OVER (PARTITION BY status ORDER BY id DESC) AS rn
        FROM customer_order_detail
        WHERE status IN :statuses
    ) ranked
    WHERE rn <= 5
    GROUP BY status
    """, nativeQuery = true)
    List<Object[]> findOrderGroupsByStatus(@Param("statuses") List<Integer> statuses);
}
