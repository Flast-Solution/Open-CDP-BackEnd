package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.WarehouseExport;
import vn.flast.models.WarehouseExportStatus;

public interface WarehouseExportStatusRepository extends JpaRepository<WarehouseExportStatus, Long> {

    @Query("FROM WarehouseExportStatus w WHERE w.name = :name")
    WarehouseExportStatus findByName(String name);

    Boolean existsByName(String name);

    @Query("From WarehouseExportStatus w WHERE w.type = 1")
    WarehouseExportStatus findByType();
}
