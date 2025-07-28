package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Table(name = "flast_note")
@Entity
@Getter @Setter
public class FlastNote {

    public static final int TYPE_COHOI = 0;
    public static final int TYPE_ORDER = 1;

    /* Object type defined */
    public static final String OBJECT_TYPE_ORDER_NOTE = "order";
    public static final String OBJECT_TYPE_LEAD = "data";

    /* Data type defined */
    public static final String DATA_TYPE_LEAD_NOTE = "lead_note";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "Mã loại đối tượng không được để trống")
    @Column(name = "object_type")
    private String objectType;

    @NotNull(message = "Mã đối tượng không được để trống")
    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "user_id")
    private Integer userId;

    @NotNull(message = "Người note không được để trống")
    @Column(name = "user_note")
    private String userNote;

    @NotNull(message = "Vui lòng nhập nội dung")
    @Column(name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
