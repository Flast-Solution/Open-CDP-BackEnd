package vn.flast.models;
/**************************************************************************/
/*  MaterialInbound.java                                                  */
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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import vn.flast.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.Date;

@DynamicInsert
@Entity
@Table(name = "material_inbound")
@Setter @Getter
@NoArgsConstructor
public class MaterialInbound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "material_id", nullable = false)
    private Long materialId;

    @Column(name = "warehouse_id", nullable = false)
    private Integer warehouseId;

    @Positive(message = "Quantity phải lớn hơn 0")
    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "width")
    private BigDecimal width;

    @Column(name = "height")
    private BigDecimal height;

    @Column(name = "source")
    private String source;

    @Column(name = "sso_id")
    private String ssoId;

    @CreationTimestamp
    @Column(name = "date")
    private Date date;

    @Column(name = "notes")
    private String note;

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
