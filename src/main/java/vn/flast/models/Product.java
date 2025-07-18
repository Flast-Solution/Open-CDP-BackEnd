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
import java.util.List;

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

    @Column(name = "code")
    private String code;

    @Column(name = "slug")
    private String slug;

    @Column(name = "provider_id")
    private Long providerId;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "updated_time")
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Transient
    private List<String> imageLists;

    @Transient
    private List<Warehouse> warehouses;
}
