package vn.flast.resultset;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SqlResultSetMapping(
        name = "CustomerLever",
        entities = @EntityResult(
                entityClass = CustomerLever.class,
                fields = {
                        @FieldResult(name = "total", column = "total"),
                        @FieldResult(name = "level", column = "level")
                }
        )
)
@Entity(name = "CustomerLever")
@Getter
@Setter
@NoArgsConstructor
public class CustomerLever {

    @Transient
    public static final String REPORT_CUSTOMER_LEVEL = "CustomerLever";

    @Id
    private Integer total;

    private Integer level;

}
