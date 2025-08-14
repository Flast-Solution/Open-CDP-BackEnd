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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.flast.models.ProductSkusDetails;
import vn.flast.models.ProductSkusPrice;
import java.util.List;

public interface ProductSkusPriceRepository extends JpaRepository<ProductSkusPrice, Integer> {

    @Query("FROM ProductSkusPrice p WHERE p.productId = :productId")
    List<ProductSkusPrice> findByProduct(Long productId);

    @Query("FROM ProductSkusPrice p WHERE p.skuId = :skuId")
    List<ProductSkusPrice> findBySkuId(Long skuId);

    @Query("FROM ProductSkusPrice p WHERE p.skuId IN (:skuId)")
    List<ProductSkusPrice> findByListSkuId(List<Long> skuId);

    @Modifying
    @Query("DELETE FROM ProductSkusPrice p WHERE p.skuId = :skuId")
    void deleteBySkuId(Long skuId);
}
