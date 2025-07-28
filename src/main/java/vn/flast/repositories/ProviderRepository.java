package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.Provider;
import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    @Query("FROM Provider p WHERE p.id IN (:ids)")
    List<Provider> findByListId(List<Long> ids);
}
