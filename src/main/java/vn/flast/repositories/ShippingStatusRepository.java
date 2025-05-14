package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.ShippingStatus;

public interface ShippingStatusRepository extends JpaRepository<ShippingStatus, Integer> {

    Boolean existsByName(String name);
}
