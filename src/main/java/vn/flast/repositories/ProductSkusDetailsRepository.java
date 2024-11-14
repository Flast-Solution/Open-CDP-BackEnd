package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.ProductSkusDetails;

import java.util.List;

public interface ProductSkusDetailsRepository extends JpaRepository<ProductSkusDetails, Integer> {

    @Query("FROM ProductSkusDetails p WHERE p.skuId = :skuId")
    List<ProductSkusDetails> findBySkuId(Long skuId);
}