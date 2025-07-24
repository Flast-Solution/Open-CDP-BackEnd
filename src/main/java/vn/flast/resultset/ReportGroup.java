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

@SqlResultSetMapping( name = "ReportGroup",  entities= {
    @EntityResult( entityClass = ReportGroup.class, fields={
        @FieldResult(name = "groupId", column="groupId"),
        @FieldResult(name = "groupName",  column="groupName"),
        @FieldResult(name = "leader",  column="leader"),
        @FieldResult(name = "total",  column="total")
    })
})

@Entity(name = "ReportGroup")
@NoArgsConstructor
@Setter @Getter
public class ReportGroup {

    @Transient
    public static final String REPORT_GROUP = "ReportGroup";

    @Id
    @Column(name = "groupId")
    private Integer groupId;

    @Column(name = "groupName")
    private String groupName;

    @Column(name = "leader")
    private String leader;

    @Column(name = "total")
    private String total;
}
