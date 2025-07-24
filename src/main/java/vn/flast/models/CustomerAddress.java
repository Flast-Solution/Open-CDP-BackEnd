package vn.flast.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Table(name = "customer_address")
@Entity
@Getter @Setter
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "name_address")
    private String nameAddress;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "address")
    private String address;

    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "is_default")
    private Long isDefault;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @JsonBackReference(value = "customerAddress")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",referencedColumnName = "id", insertable=false, updatable=false)
    private CustomerPersonal customerPersonal;
}
