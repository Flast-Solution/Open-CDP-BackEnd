package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.Media;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Integer> {

    @Query("FROM Media m WHERE m.objectId = :objectId AND m.object = :object AND m.status = 1")
    List<Media> listByObjectId(Integer objectId, String object);

    @Query("FROM Media m WHERE m.fileName = :fileName AND m.objectId = :objectId")
    Optional<Media> findFileName(String fileName, Integer objectId);
}