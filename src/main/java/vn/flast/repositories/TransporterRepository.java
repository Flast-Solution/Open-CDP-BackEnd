package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.Transporter;

public interface TransporterRepository extends JpaRepository<Transporter, Integer> {
}
