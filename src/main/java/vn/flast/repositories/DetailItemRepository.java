package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.flast.models.DetailItem;

import java.util.List;

public interface DetailItemRepository extends JpaRepository<DetailItem, Long> {

    @Query("FROM DetailItem d WHERE d.orderDetailId = :detailId")
    List<DetailItem> findByDetailId(Long detailId);

    @Query("FROM DetailItem d WHERE d.orderDetailId IN :detailIds AND d.status = 1")
    List<DetailItem> fetchDetailOrdersId(@Param("detailIds") List<Long> detailIds);

}
