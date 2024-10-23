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

@Table(name = "customer_personal")
@Entity
@Getter
@Setter
public class CustomerPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "id_card")
    private String id_card;

    @Column(name = "sale_id")
    private String sale_id;

    @Column(name = "gender")
    private String gender;

    @Column(name = "source_id")
    private Long source_id;

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

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "diem_danh_gia")
    private Date diemDanhGia;
}
