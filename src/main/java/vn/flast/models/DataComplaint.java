package vn.flast.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vn.flast.validator.PhoneNumberConstraint;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "data_complaint")
@NoArgsConstructor
@Getter
@Setter
public class DataComplaint {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "level")
    private String level;

    @Column(name = "service_id")
    private Integer serviceId;

    @NotEmpty(message = "Nhân viên tạo không được để trống")
    @Column(name = "staff", nullable = false, length = 30)
    private String staff;

    @Column(name = "source")
    private Integer source;

    @NotEmpty(message = "Tên khách hàng không được để trống")
    @Column(name = "customer_name", nullable = false, length = 200)
    private String customerName;

    @NotEmpty(message = "Số điện thoại khách hàng không được để trống")
    @PhoneNumberConstraint
    @Column(name = "customer_mobile", nullable = false, length = 20)
    private String customerMobile;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_facebook")
    private String customerFacebook = "";

    @Column(name = "chuyenmuc_id")
    private Integer chuyenMucId;

    @Column(name = "sale_id")
    private String saleId;

    @Column(name = "note", length = 65535)
    private String note;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "in_time", length = 19)
    private Date inTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", length = 19)
    private Date updateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "process_time", length = 19)
    private Date processTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "type")
    private Integer type;

    @Column(name = "kn_order_code")
    private String knOrderCode;
}
