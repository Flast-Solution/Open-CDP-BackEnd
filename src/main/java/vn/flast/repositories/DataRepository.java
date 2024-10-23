package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.Data;

public interface DataRepository extends JpaRepository<Data, Long> {
}