package vn.flast.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vn.flast.entities.ExportItem;

import java.util.Date;
import java.util.List;

@Table(name = "warehouse_export")
@Entity
@Getter @Setter
public class WarehouseExport {

    public static final int TYPE_EXPORT_NOT_ORDER = 1;
    public static final int TYPE_EXPORT_ORDER = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_code")
    private String  orderCode;

    @Column(name = "sale")
    private String  sale;

    @Column(name = "status")
    private Integer status;

    @Column(name = "warehouse_delivery_id")
    private Integer warehouseDeliveryId;

    @Column(name = "warehouse_receiving_id")
    private Integer warehouseReceivingId;

    @Column(name = "note")
    private String note;

    @Column(name = "info")
    private String info;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "in_time")
    private Date inTime;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_by")
    private Date createdBy;

    @Column(name = "type")
    private Integer type;

    @Column(name = "user_export")
    private Integer userExport;

    @Transient
    List<ExportItem> items;

    @Transient
    Warehouse warehouse;
}
