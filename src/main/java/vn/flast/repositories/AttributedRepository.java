package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.Attributed;
import java.util.Optional;

public interface AttributedRepository extends JpaRepository<Attributed, Long> {
    Optional<Attributed> findOneByNameAndValue(String name, String value);
    Optional<Attributed> findOneByName(String name);
}
