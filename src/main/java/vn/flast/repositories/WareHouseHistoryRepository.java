package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.flast.models.WareHouseHistory;

@Repository
public interface WareHouseHistoryRepository extends JpaRepository<WareHouseHistory, Integer> {
}