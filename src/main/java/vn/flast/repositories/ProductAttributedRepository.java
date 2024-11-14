package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.ProductAttributed;

public interface ProductAttributedRepository extends JpaRepository<ProductAttributed, Integer> {
}