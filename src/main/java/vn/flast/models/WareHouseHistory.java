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

@Table(name = "warehouse_history")
@Entity
@Getter @Setter
public class WareHouseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "warehouse_retain_id")
    private Long warehouseRetainId;

    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "sku_info")
    private String skuInfo;

    @Column(name = "fee")
    private Long fee;

    @Column(name = "quality")
    private Long quality;

    @Column(name = "in_time")
    private Date inTime;
}
