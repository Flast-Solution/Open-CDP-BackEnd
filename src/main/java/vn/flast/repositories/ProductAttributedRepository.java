package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.ProductAttributed;

import java.util.List;

public interface ProductAttributedRepository extends JpaRepository<ProductAttributed, Integer> {

    @Query("FROM ProductAttributed p WHERE p.productId = :productId")
    List<ProductAttributed> findByProduct(Long productId);

    @Modifying
    @Query("DELETE FROM ProductAttributed p WHERE p.productId = :productId")
    void deleteByProductId(Long productId);
}