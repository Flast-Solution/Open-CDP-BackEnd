package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.CustomerOrder;
import vn.flast.models.Data;

public interface DataRepository extends JpaRepository<Data, Long> {

    @Query("FROM data d WHERE d.customerMobile = :phone")
    Data findFirstByPhone(String phone);

}