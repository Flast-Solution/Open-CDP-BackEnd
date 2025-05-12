package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.CustomerOrderNote;

public interface CustomerOrderNoteRepository extends JpaRepository<CustomerOrderNote, Long> {

    @Query("FROM CustomerOrderNote c WHERE c.orderCode =:orderCode")
    CustomerOrderNote findByOrderCode(String orderCode);
}
