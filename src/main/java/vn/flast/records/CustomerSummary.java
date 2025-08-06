package vn.flast.records;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SqlResultSetMapping(
    name = "CustomerSummary",
    entities = @EntityResult(entityClass = CustomerSummary.class, fields = {
        @FieldResult(name = "opportunities", column = "opportunities"),
        @FieldResult(name = "orders", column = "orders"),
        @FieldResult(name = "leads", column = "leads")
    })
)
@Entity(name = "CustomerSummary")
@Getter @Setter
@NoArgsConstructor
public class CustomerSummary {
    @Id
    private Integer opportunities;
    private Integer orders;
    private Integer leads;
}
