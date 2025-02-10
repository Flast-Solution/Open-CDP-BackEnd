package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "provider")
@Entity
@Getter @Setter
public class Provider {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "geolocation")
    private String geolocation;

    @NotBlank(message = "Mã Nhà in không được để trống!")
    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @NotNull(message = "Tên Nhà in không được để trống!")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Người đại diện không được để trống!")
    @Column(name = "representative")
    private String representative;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @Column(name = "phone_contact", length = 20)
    private String phoneContact;

    @Column(name = "email")
    private String email;

    @Column(name = "email_manufacture")
    private String emailManufacture;

    @NotBlank(message = "Địa chỉ không được để trống!")
    @Column(name = "address")
    private String address;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_owner")
    private String bankOwner;

    @Column(name = "strengths")
    private String strengths;

    @Column(name = "note")
    private String note;

    @Column(name = "status", columnDefinition = "integer default 1")
    private Integer status;

    @Column(name = "province_id")
    private Integer provinceId;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
}
