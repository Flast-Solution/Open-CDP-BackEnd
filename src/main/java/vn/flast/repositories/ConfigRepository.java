package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.Config;

public interface ConfigRepository extends JpaRepository<Config, Integer> {

    Config findByKey(String key);
}
