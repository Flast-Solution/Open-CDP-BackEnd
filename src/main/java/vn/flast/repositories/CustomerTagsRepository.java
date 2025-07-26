package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.flast.models.CustomerTags;
import java.util.List;

public interface CustomerTagsRepository extends JpaRepository<CustomerTags, Long> {

    @Query("FROM CustomerTags p WHERE p.customerId IN (:customersId)")
    List<CustomerTags> findByListId(@Param("customersId") List<Long> customersId);

    @Query("FROM CustomerTags p WHERE p.customerId = :customerId")
    List<CustomerTags> findByCustomerId(Long customerId);

    @Modifying
    @Query("DELETE FROM CustomerTags ct WHERE ct.customerId = :customerId")
    void deleteByCustomerId(@Param("customerId") Long customerId);
}
