package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.ProductSkusPrice;

public interface ProductSkusPriceRepository extends JpaRepository<ProductSkusPrice, Integer> {
}