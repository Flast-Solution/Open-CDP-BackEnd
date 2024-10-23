package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.DataMedia;

public interface DataMediaRepository extends JpaRepository<DataMedia, Long> {
}