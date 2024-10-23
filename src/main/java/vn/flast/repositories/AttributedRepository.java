package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.Attributed;

public interface AttributedRepository extends JpaRepository<Attributed, Long> {
}