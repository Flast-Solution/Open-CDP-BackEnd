package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.ProductSkus;

public interface ProductSkusRepository extends JpaRepository<ProductSkus, Integer> {
}