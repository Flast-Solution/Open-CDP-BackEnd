package vn.flast.resultset;

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

@SqlResultSetMapping( name = "ReportSaleTotal",  entities= {
    @EntityResult( entityClass = ReportSaleTotal.class, fields={
        @FieldResult(name = "total", column="total"),
        @FieldResult(name = "sale",  column="sale")
    })
})
@Entity(name = "ReportSaleTotal")
@NoArgsConstructor
@Setter @Getter
public class ReportSaleTotal {

    @Transient
    public static final String REPORT_SALE_TOTAL = "ReportSaleTotal";

    @Column(name = "total")
    private Long total;
    @Id
    @Column(name = "sale")
    private String sale;
}
