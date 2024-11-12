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

@Table(name = "product")
@Entity
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

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

    @Column(name = "provider_code")
    private String providerCode;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price")
    private Integer price;

    @Column(name = "price_ref")
    private Long price_ref;

    @Column(name = "seo_title")
    private String seo_title;

    @Column(name = "seo_description")
    private String seoDescription;

    @Column(name = "seo_content")
    private String seoContent;

    @Column(name = "image")
    private String image;

    @Column(name = "attributed")
    private String attributed;

    @Column(name = "social")
    private String social;

    @Column(name = "status")
    private Long status;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;
}
