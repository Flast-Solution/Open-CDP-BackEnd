package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.Warehouse;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    @Query("FROM Warehouse w WHERE w.providerId = :providerId AND w.productId = :productId AND w.skuId = :skuId AND w.stockId = :stockId")
    Warehouse findProductStock(Long providerId, Long productId, Long skuId, Integer stockId);

    @Query("FROM Warehouse w WHERE w.productId = :productId")
    List<Warehouse> findByProductId(Long productId);

    @Query("FROM Warehouse w WHERE w.id IN (:ids)")
    List<Warehouse> findByIds(List<Integer> ids);
}