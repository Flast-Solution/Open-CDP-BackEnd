package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    @Query("FROM Warehouse w WHERE w.providerId = :providerId AND w.productId = :productId AND w.skuId = :skuId")
    Warehouse findProductStock(Long providerId, Long productId, Long skuId);
}