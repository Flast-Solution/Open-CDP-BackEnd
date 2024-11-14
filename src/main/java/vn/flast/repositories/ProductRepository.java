package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("FORM Product p WHERE p.code = :code")
    Product findByCode(String code);
}