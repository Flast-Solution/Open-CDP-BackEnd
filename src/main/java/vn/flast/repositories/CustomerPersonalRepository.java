package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.CustomerPersonal;

public interface CustomerPersonalRepository extends JpaRepository<CustomerPersonal, Long> {
}