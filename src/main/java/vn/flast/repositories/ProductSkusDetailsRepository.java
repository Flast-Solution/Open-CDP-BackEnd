package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.ProductSkusDetails;

public interface ProductSkusDetailsRepository extends JpaRepository<ProductSkusDetails, Integer> {
}