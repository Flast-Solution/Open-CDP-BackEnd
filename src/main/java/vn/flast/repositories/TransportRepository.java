package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.Transport;

import java.util.List;

public interface TransportRepository extends JpaRepository<Transport, Integer> {

    @Query("FROM Transport t WHERE t.orderId = :orderId")
    List<Transport> findByOrderId(Long orderId);

}
