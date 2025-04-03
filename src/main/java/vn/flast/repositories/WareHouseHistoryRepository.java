package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.flast.models.WareHouseHistory;

import java.time.LocalDate;

@Repository
public interface WareHouseHistoryRepository extends JpaRepository<WareHouseHistory, Integer> {
    @Query("SELECT COUNT(w) FROM WareHouseHistory w WHERE DATE(w.inTime) = :date")
    long countByCreatedDate(@Param("date") LocalDate date);
}