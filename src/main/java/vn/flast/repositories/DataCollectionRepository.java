package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.DataCollection;

public interface DataCollectionRepository extends JpaRepository<DataCollection, Integer> {
}
