package vn.flast.records;
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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SqlResultSetMapping( name = "ReportLeadSale",  entities= {
    @EntityResult( entityClass = ReportLeadSale.class, fields={
        @FieldResult(name = "count", column="count"),
        @FieldResult(name = "status", column="status"),
        @FieldResult(name = "sale",  column="sale")
    })
})
@Entity(name = "ReportLeadSale")
@NoArgsConstructor
@Setter @Getter
public class ReportLeadSale {

    @Transient
    public static final String REPORT_LEAD_SALE = "ReportLeadSale";

    @Column(name = "count")
    private Integer count;

    @Column(name = "status")
    private String status;

    @Id
    @Column(name = "sale")
    private String sale;
}
