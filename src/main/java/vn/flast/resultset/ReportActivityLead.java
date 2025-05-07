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

@SqlResultSetMapping( name = "ReportActivityLead",  entities= {
        @EntityResult( entityClass = ReportActivityLead.class, fields={
                @FieldResult(name = "date", column="date"),
                @FieldResult(name = "saleId",  column="saleId"),
                @FieldResult(name = "total",  column="total")
        })
})
@Entity(name = "ReportActivityLead")
@NoArgsConstructor
@Setter
@Getter
public class ReportActivityLead {



    @Transient
    public static final String REPORT_ACTIVITY_LEAD = "ReportActivityLead";
    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;
    @Id
    @Column(name = "saleId")
    private Integer saleId;
    @Id
    @Column(name = "total")
    private Integer total;
}
