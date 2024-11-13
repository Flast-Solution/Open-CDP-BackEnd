package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Table(name = "product")
@Entity
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "quality_in_stock")
    private Long qualityInStock;

    @Column(name = "total_import_stock")
    private Integer totalImportStock;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price")
    private Integer price;

    @Column(name = "price_ref")
    private Long priceRef;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description")
    private String seoDescription;

    @Column(name = "seo_content")
    private String seoContent;

    @Column(name = "image")
    private String image;

    @Column(name = "social")
    private String social;

    @Column(name = "status")
    private Long status;

    @Column(name = "created_time")
    @CreationTimestamp
    private Date createdTime;

    @Column(name = "updated_time")
    @UpdateTimestamp
    private Date updatedTime;
}
