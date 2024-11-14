package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.ProductSkus;

import java.util.List;

public interface ProductSkusRepository extends JpaRepository<ProductSkus, Long> {


    List<ProductSkus> findByProductId(Long id);
}