package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.WarehouseExport;
import java.util.Optional;

public interface WarehouseExportRepository extends JpaRepository<WarehouseExport, Integer> {
    @Query("FROM WarehouseExport WHERE orderId = :orderId")
    Optional<WarehouseExport> findByOrderId(Long orderId);
}
