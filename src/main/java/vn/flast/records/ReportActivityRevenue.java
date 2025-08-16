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




import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;

@SqlResultSetMapping( name = "ReportActivityRevenue",  entities= {
    @EntityResult( entityClass = ReportActivityRevenue.class, fields={
        @FieldResult(name = "date", column="date"),
        @FieldResult(name = "order",  column="order"),
        @FieldResult(name = "total",  column="total"),
        @FieldResult(name = "cohoi",  column="cohoi")
    })
})
@Entity(name = "ReportActivityRevenue")
@NoArgsConstructor
@Setter @Getter
public class ReportActivityRevenue {

    @Transient
    public static final String REPORT_ACTIVITY_REVENUE = "ReportActivityRevenue";

    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;

    @Column(name = "order")
    private Integer order;

    @Column(name = "total")
    private Long total;

    @Column(name = "cohoi")
    private Integer cohoi;
}
