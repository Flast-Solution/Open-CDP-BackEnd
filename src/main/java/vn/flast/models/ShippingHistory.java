package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import vn.flast.utils.NumberUtils;
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

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_mobile")
    private String receiverMobile;

    @Column(name = "sso_id")
    private String ssoId;

    @Column(name = "warehouse_id")
    private Integer warehouseId;

    @Column(name = "transport_name")
    private String transportName;

    @Column(name = "transport_code")
    private String transporterCode;

    @Column(name = "transport_id", nullable = false)
    private Integer transporterId;

    @Column(name = "shipping_cost")
    private Integer shippingCost;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "province_id")
    private Integer provinceId;

    @Column(name = "ward_id")
    private Integer wardId;

    @Column(name = "address")
    private String address;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private Integer status;

    @CreationTimestamp
    @Column(name = "in_time")
    private Date inTime;

    @PrePersist
    public void beforeSave() {
        if(NumberUtils.isNull(status)) {
            status = 0;
        }
    }

    @Transient
    private WarehouseProduct warehouseProduct;
}
