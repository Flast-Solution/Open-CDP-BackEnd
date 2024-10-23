package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.UserKpi;

public interface UserKpiRepository extends JpaRepository<UserKpi, Integer> {
}