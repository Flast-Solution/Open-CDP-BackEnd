package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.ShippingHistory;

public interface ShippingHistoryRepository extends JpaRepository<ShippingHistory, Integer> {
}