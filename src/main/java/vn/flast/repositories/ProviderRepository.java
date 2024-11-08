package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
