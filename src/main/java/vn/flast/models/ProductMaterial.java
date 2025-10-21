package vn.flast.models;
/**************************************************************************/
/*  ProductMaterial.java                                                  */
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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import vn.flast.utils.NumberUtils;
import java.math.BigDecimal;

@Table(name = "product_material")
@Entity
@Getter @Setter
public class ProductMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "sku_id", nullable = false)
    private Long skuId;

    @Column(name = "material_id", nullable = false)
    private Long materialId;

    @Column(name = "material_unit")
    private String materialUnit;

    @Positive(message = "Quantity phải lớn hơn 0")
    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "width")
    private BigDecimal width;

    @Column(name = "height")
    private BigDecimal height;

    @Positive(message = "Đơn gía phải lớn hơn 0")
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "sso_id")
    private String ssoId;

    @PrePersist
    public void beforePersist() {
        if(NumberUtils.isNull(width)) {
            width = BigDecimal.valueOf(0.0);
        }
        if(NumberUtils.isNull(height)) {
            height = BigDecimal.valueOf(0.0);
        }
    }
}
