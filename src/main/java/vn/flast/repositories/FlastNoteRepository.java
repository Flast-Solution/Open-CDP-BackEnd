package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.flast.models.FlastNote;
import java.util.List;

public interface FlastNoteRepository extends JpaRepository<FlastNote, Long> {
    @Query("FROM FlastNote c WHERE c.objectType = :objectType AND objectId = :objectId")
    FlastNote findByTypeId(String objectType, Long objectId);

    @Query(value = "SELECT n.* FROM flast_note as n LEFT JOIN data as l on l.id = n.object_id WHERE l.customer_mobile = :mobile", nativeQuery = true)
    List<FlastNote> fetchMobileOfLead(@Param("mobile") String mobile);
}
