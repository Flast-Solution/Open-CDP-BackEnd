package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.flast.models.ProductSkus;

import java.util.List;

public interface ProductSkusRepository extends JpaRepository<ProductSkus, Long> {


    List<ProductSkus> findByProductId(Long id);

    @Modifying
    @Query("UPDATE ProductSkus p SET p.del = 1 WHERE p.productId = :productId AND p.id IN :ids")
    void updateDelProductSkus(@Param("productId") Long productId, @Param("ids") List<Long> ids);


}