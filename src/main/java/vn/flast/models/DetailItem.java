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

import java.util.Date;

@Table(name = "detail_item")
@Entity
@Getter
@Setter
public class DetailItem {

    public static int STATUS_HOAT_DONG = 1;
    public static int STATUS_HUY = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "order_detail_id", nullable = false)
    private Long orderDetailId;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "sku_info")
    private String skuInfo;

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price_off")
    private Double priceOff;

    @Column(name = "total")
    private Double total;

    @Column(name = "discount")
    private String discount;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status")
    private Integer status = STATUS_HOAT_DONG;

    @Transient
    private Integer warehouseId;
}
