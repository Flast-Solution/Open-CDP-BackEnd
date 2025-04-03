package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.WareHouseStatus;
import vn.flast.models.WareHouseStock;

public interface WarehouseStockRepository extends JpaRepository<WareHouseStock, Integer> {
    @Query("FROM WareHouseStatus w WHERE w.name = :name")
    WareHouseStatus findByName(String name);

    Boolean existsByName(String name);
}
