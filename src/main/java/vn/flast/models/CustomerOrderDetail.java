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

@Table(name = "customer_order_detail")
@Entity
@Getter @Setter
public class CustomerOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "customer_order_id", nullable = false)
    private Long customerOrderId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "product_info")
    private String productInfo;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "sku_name")
    private String skuName;

    @Column(name = "sku_info")
    private String skuInfo;

    @Column(name = "price")
    private Long price;

    @Column(name = "price_off")
    private Long priceOff;

    @Column(name = "total")
    private Long total;

    @Column(name = "ship_status")
    private Integer shipStatus;

    @Column(name = "ship_done_at")
    private Date shipDoneAt;
}
