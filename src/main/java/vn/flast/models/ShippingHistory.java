package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Table(name = "shipping_history")
@Entity
@Getter @Setter
public class ShippingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "detail_code")
    private String detailCode;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "warehouse_id")
    private Long warehouse_id;

    @Column(name = "transport_name")
    private String transportName;

    @Column(name = "transporter_id")
    private Long transporterId;

    @Column(name = "shipping_cost")
    private Long shippingCost;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "in_time")
    private Date inTime;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private Long status;
}
