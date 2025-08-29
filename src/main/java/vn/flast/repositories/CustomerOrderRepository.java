package vn.flast.repositories;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.flast.models.CustomerOrder;
import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    @Query("FROM CustomerOrder c WHERE c.code =:code")
    Optional<CustomerOrder> findByCode(String code);

    @Query("SELECT o FROM CustomerOrder o WHERE o.id = :id")
    Optional<CustomerOrder> fetchWithCustomer(@Param("id") Long id);

    @Query("FROM CustomerOrder c WHERE c.id IN (:ids)")
    List<CustomerOrder> findByIds(List<Long> ids);

    @Query("SELECT COUNT(c.id) FROM CustomerOrder c WHERE c.customerId = :id AND c.type = :type")
    Integer countOrder(@Param("id") Long id, @Param("type") String type);

    @Query(value = "SELECT c.* FROM customer_order c WHERE c.customer_id = :customerId AND c.type = :type LIMIT :limit", nativeQuery = true)
    List<CustomerOrder> findByCustomerId(Long customerId, String type, Integer limit);
}
