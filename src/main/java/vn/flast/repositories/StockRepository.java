package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {
}