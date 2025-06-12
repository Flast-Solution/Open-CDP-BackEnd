package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.flast.models.ProductSkusDetails;

import java.util.List;

public interface ProductSkusDetailsRepository extends JpaRepository<ProductSkusDetails, Integer> {

    @Query("FROM ProductSkusDetails p WHERE p.skuId = :skuId")
    List<ProductSkusDetails> findBySkuId(Long skuId);

    @Modifying
    @Query("UPDATE ProductSkusDetails p SET p.del = 1 WHERE p.productId = :productId AND p.id IN :ids")
    void updateDelProductSkus(@Param("productId") Long productId, @Param("ids") List<Integer> ids);

    @Query("FROM ProductSkusDetails p WHERE p.id IN (:ids)")
    List<ProductSkusDetails> findByListId(List<Long> ids);
}