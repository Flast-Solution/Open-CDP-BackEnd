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
import vn.flast.models.WarehouseProduct;
import java.util.List;

public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Integer> {

    @Query("FROM WarehouseProduct w WHERE w.productId = :productId")
    List<WarehouseProduct> findByProductId(Long productId);

    @Query("FROM WarehouseProduct w WHERE w.skuId = :skuId AND stockId = :stockId")
    List<WarehouseProduct> findBySkuAndStockId(Long skuId, Integer stockId);
}
