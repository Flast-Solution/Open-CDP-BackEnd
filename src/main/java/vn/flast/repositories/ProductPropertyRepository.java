package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.ProductProperty;

import java.util.List;

public interface ProductPropertyRepository extends JpaRepository<ProductProperty, Integer> {

    @Query("FROM ProductProperty p WHERE p.productId = :productId")
    List<ProductProperty> findByProductId(Long productId);

    @Modifying
    @Query("DELETE FROM ProductProperty p WHERE p.productId = :productId")
    void deleteByProductId(Long productId);
}