package vn.flast.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "product_image")
@Entity
@Getter
@Setter
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "section_id")
    private Long section_id;

    @Column(name = "is_featured")
    private Long isFeatured;

    @Column(name = "is_slideshow")
    private Long isSlideshow;

}
