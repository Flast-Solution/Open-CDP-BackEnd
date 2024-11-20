package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Entity
@Table(name = "customer_order_payment")
@Getter @Setter
public class CustomerOrderPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Size(max = 100)
    @Column(name = "code", length = 100)
    private String code;

    @Column(name = "method")
    private String method;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "sso_id")
    private String ssoId;

    @Column(name = "is_confirm")
    private Integer isConfirm;

    @Column(name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "in_time", nullable = false)
    private Date inTime;

    @Column(name = "confirm_time")
    private Date confirmTime;
}
