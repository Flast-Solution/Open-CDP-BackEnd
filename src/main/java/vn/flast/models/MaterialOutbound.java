package vn.flast.models;
/**************************************************************************/
/*  MaterialOutbound.java                                                 */
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

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.*;
import java.math.BigDecimal;

@DynamicInsert
@Entity
@Table(name = "material_outbound")
@Setter @Getter
@NoArgsConstructor
public class MaterialOutbound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "warehouse_id", nullable = false)
    private Integer warehouseId;

    @Column(name = "manufacture_code")
    private String manufactureCode;

    @Column(name = "material_id", nullable = false)
    private Long materialId;

    @Positive(message = "Quantity phải lớn hơn 0")
    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "width")
    private BigDecimal width;

    @Column(name = "height")
    private BigDecimal height;

    @Column(name = "sso_id")
    private String ssoId;

    @CreationTimestamp
    @Column(name = "date")
    private String date;
}
