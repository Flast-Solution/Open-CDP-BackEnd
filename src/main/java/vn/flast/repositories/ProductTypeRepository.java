package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}