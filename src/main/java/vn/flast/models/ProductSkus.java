package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import vn.flast.entities.PriceRange;
import vn.flast.entities.SkuAttributed;

import java.util.List;

@Table(name = "product_skus")
@Entity
@Getter @Setter
public class ProductSkus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Transient
    private List<PriceRange> listPriceRange;

    @Transient
    private List<SkuAttributed> sku;
}
