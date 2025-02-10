package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.Stock;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    @Query("FROM Stock s WHERE s.status = 1")
    List<Stock> fetchStockStatus();
}