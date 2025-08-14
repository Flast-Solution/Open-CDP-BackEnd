package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "data_care")
@NoArgsConstructor
@Getter @Setter
public class DataCare implements Cloneable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "data_id")
    private Long dataId;

    @Column(name = "information")
    private String information ;

    @Column(name = "cause")
    private String cause;

    @Column(name = "priority")
    private String priority;

    @Column(name = "note")
    private String note;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date inTime;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public DataCare clone() {
        try {
            return (DataCare) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
