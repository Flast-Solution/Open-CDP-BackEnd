package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.flast.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("FROM Product p WHERE p.code = :code")
    Product findByCode(String code);

    @Query(value = "SELECT p.name FROM product p LEFT JOIN product_skus s ON p.id = s.product_id WHERE s.id = :skuId", nativeQuery = true)
    String findNameBySkuId(@Param("skuId") Long skuId);

    @Query("FROM Product p WHERE p.name = :name")
    Optional<Product> findByName(String name);

    @Query("FROM Product p WHERE p.id IN (:ids)")
    List<Product> findByListId(List<Long> ids);


}
