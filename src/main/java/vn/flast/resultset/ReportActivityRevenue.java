package vn.flast.resultset;


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
@Setter
@Getter
public class ReportActivityRevenue {



    @Transient
    public static final String REPORT_ACTIVITY_REVENUE = "ReportActivityRevenue";
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;
    @Id
    @Column(name = "order")
    private Integer order;
    @Id
    @Column(name = "total")
    private Long total;

    @Id
    @Column(name = "cohoi")
    private Integer cohoi;
}
