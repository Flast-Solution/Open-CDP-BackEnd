package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.Province;
import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    @Query("FROM Province p WHERE (:id IS NULL OR p.parentId = (:id))")
    List<Province> listByParent(Integer id);
}
