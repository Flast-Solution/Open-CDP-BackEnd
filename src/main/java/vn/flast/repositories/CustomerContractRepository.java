package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.CustomerContract;
import java.util.List;

public interface CustomerContractRepository extends JpaRepository<CustomerContract, Integer> {
    @Query("FROM CustomerContract p WHERE p.orderCode =:orderCode")
    List<CustomerContract> findByCode(String orderCode);

    @Modifying
    @Query("DELETE FROM CustomerContract p WHERE p.orderCode = :orderCode AND p.fileName = :fileName")
    void deleteByOrderCode(String orderCode, String fileName);
}
