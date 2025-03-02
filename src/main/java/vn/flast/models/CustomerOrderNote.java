package vn.flast.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "note")
    private String note;

    @Column(name = "type")
    private Integer type;


}
