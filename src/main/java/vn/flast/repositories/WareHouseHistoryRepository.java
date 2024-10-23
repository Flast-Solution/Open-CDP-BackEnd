package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.WareHouseHistory;

public interface WareHouseHistoryRepository extends JpaRepository<WareHouseHistory, Integer> {
}