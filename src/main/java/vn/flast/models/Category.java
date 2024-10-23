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

@Table(name = "attributed")
@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "name")
    private String name;

    @Column(name = "slug")
    private String slug;

    @Column(name = "status")
    private Long status;

    @Column(name = "icon")
    private String icon;

    @Column(name = "image")
    private String image;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "seo_title")
    private String seo_title;

    @Column(name = "seo_description")
    private String seoDescription;

    @Column(name = "seo_keyword")
    private String seoKeyword;

    @Column(name = "seo_content")
    private String seoContent;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;


}
