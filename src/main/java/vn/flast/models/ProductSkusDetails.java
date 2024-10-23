package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "product_skus_details")
@Entity
@Getter
@Setter
public class ProductSkusDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "product_skus_id")
    private Long productSkusId;

    @Column(name = "priduct_unit")
    private String priductUnit;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity_from")
    private Long quantityFrom;

    @Column(name = "quantity_to")
    private Long quantityTo;

    @Column(name = "price_ref")
    private Long priceRef;

    @Column(name = "price")
    private Long price;

    @Column(name = "price_import")
    private Long price_import;
}
