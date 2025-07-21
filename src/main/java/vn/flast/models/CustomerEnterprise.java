package vn.flast.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Table(name = "customer_enterprise")
@Entity
@Getter @Setter
public class CustomerEnterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "total_fee")
    private Long totalFee;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "director")
    private String director;

    @Column(name = "address")
    private String address;

    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "in_time")
    private Date inTime;

    @Column(name = "contract_file")
    private String contractFile;

    @PrePersist
    @PreUpdate
    public void beforeCreate() {
        inTime = new Date();
    }
}
