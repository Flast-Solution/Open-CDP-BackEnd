package vn.flast.records;

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
