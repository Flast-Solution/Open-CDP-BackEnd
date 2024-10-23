package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}