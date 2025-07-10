package vn.flast.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import vn.flast.utils.NumberUtils;
import java.util.Date;

@Table(name = "warehouse")
@Entity
@Getter @Setter
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "stock_id")
    private Integer stockId;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "provider_id")
    private Long providerId;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "sku_info")
    private String skuInfo;

    @Column(name = "fee")
    private Long fee;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "total")
    private Long total;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "sku_name")
    private String skuName;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "in_time")
    private Date inTime;

    @PrePersist
    public void beforeCreate() {
        if(NumberUtils.isNull(fee)) {
            fee = 0L;
        }
        total = quantity;
    }
}
