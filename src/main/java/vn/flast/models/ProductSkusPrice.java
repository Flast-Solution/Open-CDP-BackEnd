package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "product_skus_price")
@Entity
@Getter @Setter
public class ProductSkusPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity_from")
    private Long quantityFrom;

    @Column(name = "quantity_to")
    private Long quantityTo;

    @Column(name = "price_ref")
    private Long priceRef = 0L;

    @Column(name = "price")
    private Long price;

    @Column(name = "price_import")
    private Long priceImport = 0L;
}
