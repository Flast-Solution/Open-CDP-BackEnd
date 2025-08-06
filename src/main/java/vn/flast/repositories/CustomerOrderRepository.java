package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.flast.models.CustomerOrder;
import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    @Query("FROM CustomerOrder c WHERE c.code =:code")
    Optional<CustomerOrder> findByCode(String code);

    @Query("SELECT o FROM CustomerOrder o WHERE o.id = :id")
    Optional<CustomerOrder> fetchWithCustomer(@Param("id") Long id);

    @Query("FROM CustomerOrder c WHERE c.id IN (:ids)")
    List<CustomerOrder> findByIds(List<Long> ids);

    @Query("SELECT COUNT(c.id) FROM CustomerOrder c WHERE c.customerId = :id AND c.type = :type")
    Integer countOrder(@Param("id") Long id, @Param("type") String type);

    @Query(value = "SELECT c.* FROM customer_order c WHERE c.customer_id = :customerId AND c.type = :type LIMIT 5", nativeQuery = true)
    List<CustomerOrder> findByCustomerId(Long customerId, String type);
}
