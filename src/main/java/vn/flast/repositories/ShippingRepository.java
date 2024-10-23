package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.Shipping;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {
}