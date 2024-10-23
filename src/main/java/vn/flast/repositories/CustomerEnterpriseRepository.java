package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.CustomerEnterprise;

public interface CustomerEnterpriseRepository extends JpaRepository<CustomerEnterprise, Long> {
}