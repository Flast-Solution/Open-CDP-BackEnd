package vn.flast.models;
/**************************************************************************/
/*  Materials.java                                                        */
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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import vn.flast.domains.material.UnitType;
import java.math.BigDecimal;
import java.util.Collection;

@DynamicInsert
@Entity
@Table(name = "materials")
@Setter @Getter
@NoArgsConstructor
public class Materials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_type", nullable = false)
    private UnitType unitType;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "`description`")
    private String description;

    @Positive(message = "Giá phải lớn hơn 0")
    @Column(name = "price_per_unit")
    private BigDecimal pricePerUnit;

    @CreationTimestamp
    @Column(name = "created_at")
    private String createdAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY)
    private Collection<MaterialInventory> inventory;
}
