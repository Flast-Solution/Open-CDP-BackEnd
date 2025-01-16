package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Table(name = "customer_personal")
@Entity
@Getter
@Setter
public class CustomerPersonal {

    public static final Integer KH_MOI = 1;
    public static final Integer KH_THAN_THIET = 2;
    public static final Integer KH_TIEM_NANG = 3;
    public static final Integer KH_VIP = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "sale_id")
    private Integer saleId;

    @Column(name = "gender")
    private String gender;

    @Column(name = "source_id")
    private Long sourceId;

    @Column(name = "level")
    private Long level;

    @Column(name = "facebook_id")
    private String facebookId;

    @Column(name = "name")
    private String name;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "address")
    private String address;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "email")
    private String email;

    @Column(name = "is_trust_email")
    private Long isTrustEmail;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

    @Column(name = "token_confirm")
    private String tokenConfirm;

    @Column(name = "status")
    private Long status;

    @Column(name = "num_of_order")
    private Integer numOfOrder = 0;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "diem_danh_gia")
    private Integer diemDanhGia;

    @PrePersist
    private void beforeSave() {
        if(StringUtils.isEmpty(gender)) {
            gender = "other";
        }
        type = "newCustomer";
    }
}
