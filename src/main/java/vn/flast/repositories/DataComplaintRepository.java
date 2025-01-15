package vn.flast.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.flast.models.DataComplaint;

public interface DataComplaintRepository extends JpaRepository<DataComplaint, Integer> {
}
