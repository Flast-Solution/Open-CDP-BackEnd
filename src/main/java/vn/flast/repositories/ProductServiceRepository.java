package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.ProductService;

public interface ProductServiceRepository extends JpaRepository<ProductService, Long> {
}