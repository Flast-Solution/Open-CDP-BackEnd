package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.ProductProperty;

public interface ProductPropertyRepository extends JpaRepository<ProductProperty, Integer> {
}