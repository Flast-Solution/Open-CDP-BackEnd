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

import java.util.List;

@Table(name = "product_attributed")
@Entity
@Getter @Setter
public class ProductAttributed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "attributed_id")
    private Integer attributedId;

    @Column(name = "attributed_value_id")
    private Integer attributedValueId;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Transient
    private List<Integer> propertyValueId;
}
