package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.FlastNote;

public interface CustomerOrderNoteRepository extends JpaRepository<FlastNote, Long> {
    @Query("FROM FlastNote c WHERE c.objectType = :objectType AND objectId = :objectId")
    FlastNote findByTypeId(String objectType, Long objectId);
}
