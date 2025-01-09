package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "customer_personal")
@Getter @Setter
public class Customer {

    public Customer() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "gender")
    private String gender;

    @Column(name = "sale_id")
    private Integer saleId;

    @Column(name = "source_id")
    private Integer sourceId;

    @Column(name = "level")
    private Boolean level;

    @Size(max = 255)
    @Column(name = "facebook_id")
    private String facebookId;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "province_id", columnDefinition = "INT UNSIGNED")
    private Long provinceId;

    @Column(name = "district_id", columnDefinition = "INT UNSIGNED")
    private Long districtId;

    @Column(name = "ward_id", columnDefinition = "INT UNSIGNED")
    private Long wardId;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Size(max = 255)
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_id")
    private Integer companyId;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "is_trust_email")
    private Integer isTrustEmail;

    @Size(max = 20)
    @Column(name = "mobile", length = 20)
    private String mobile;

    @Size(max = 255)
    @Column(name = "password")
    private String password;

    @Size(max = 200)
    @Column(name = "token_confirm", length = 200)
    private String tokenConfirm;

    @Column(name = "status", columnDefinition = "INT UNSIGNED")
    private Long status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "diem_danh_gia", precision = 8, scale = 2)
    private BigDecimal diemDanhGia;

    @PrePersist
    private void beforeSave() {
        if(StringUtils.isEmpty(gender)) {
            gender = "other";
        }
        type = "newCustomer";
    }
}
