package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
}