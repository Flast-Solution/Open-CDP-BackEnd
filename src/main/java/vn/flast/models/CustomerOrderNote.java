package vn.flast.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "customer_order_note")
@Entity
@Getter
@Setter
public class CustomerOrderNote {

    public static final int NOTE_NOIBO = 0;
    public static final int NOTE_KHACHHANG = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "note")
    private String note;

    @Column(name = "user_id")
    private Integer usesId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_note")
    private String userNote;

    @Column(name = "type")
    private Integer type;

    @Column(name = "cause")
    private Integer cause;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;


}
