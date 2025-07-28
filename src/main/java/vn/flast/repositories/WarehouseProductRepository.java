package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.WarehouseProduct;
import java.util.List;

public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Integer> {

    @Query("FROM WarehouseProduct w WHERE w.providerId = :providerId AND w.productId = :productId AND w.skuId = :skuId AND w.stockId = :stockId")
    WarehouseProduct findProductStock(Long providerId, Long productId, Long skuId, Integer stockId);

    @Query("FROM WarehouseProduct w WHERE w.productId = :productId")
    List<WarehouseProduct> findByProductId(Long productId);

    @Query("FROM WarehouseProduct w WHERE w.id IN (:ids)")
    List<WarehouseProduct> findByIds(List<Integer> ids);

    @Query("FROM WarehouseProduct w WHERE  w.productId = :productId AND w.skuId = :skuId AND w.stockId = :stockId")
    WarehouseProduct findProductSku(Long productId, Long skuId, Integer stockId);
}
