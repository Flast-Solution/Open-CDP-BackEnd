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

    @Column(name = "sso_id")
    private String ssoId;

    @Column(name = "warehouse_id")
    private Integer warehouseId;

    @Column(name = "transport_name")
    private String transportName;

    @Column(name = "transporter_id", nullable = false)
    private Long transporterId;

    @Column(name = "shipping_cost")
    private Long shippingCost;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "ward_id")
    private Long wardId;

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
