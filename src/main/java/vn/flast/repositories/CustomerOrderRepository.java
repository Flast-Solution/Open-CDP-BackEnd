package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.CustomerOrder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    @Query("FROM CustomerOrder c WHERE c.code =:code")
    Optional<CustomerOrder> findByCode(String code);

    @Query("FROM CustomerOrder c WHERE c.code IN (:codes)")
    List<CustomerOrder> findByCodes(Collection<String> codes);
}
