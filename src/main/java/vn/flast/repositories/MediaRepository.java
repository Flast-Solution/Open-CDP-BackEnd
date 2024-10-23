package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.Media;

public interface MediaRepository extends JpaRepository<Media, Integer> {
}