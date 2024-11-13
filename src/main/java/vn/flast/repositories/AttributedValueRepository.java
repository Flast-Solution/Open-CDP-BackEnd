package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.AttributedValue;
import java.util.List;
import java.util.Optional;

public interface AttributedValueRepository extends JpaRepository<AttributedValue, Long> {
    @Query("FROM AttributedValue a WHERE a.attributedId =:attributedId")
    List<AttributedValue> findByAttrId(Integer attributedId);

    @Query(value = "SELECT * FROM attributed_value a WHERE a.attributed_id =:attributedId AND a.value =:value LIMIT 1", nativeQuery = true)
    Optional<AttributedValue> findAttrIdAndValue(Integer attributedId, String value);
}
