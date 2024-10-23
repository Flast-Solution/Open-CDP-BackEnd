package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.DataOwner;

public interface DataOwnerRepository extends JpaRepository<DataOwner, Long> {
}