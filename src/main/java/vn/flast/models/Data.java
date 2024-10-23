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

@Table(name = "customer_persional")
@Entity
@Getter
@Setter
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "level")
    private String level;

    @Column(name = "staff")
    private String staff;

    @Column(name = "province_name")
    private String provinceName;

    @Column(name = "source")
    private Long source;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_mobile")
    private String customerMobile;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_facebook")
    private String customerFacebook;

    @Column(name = "tags")
    private String tags;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "sale_id")
    private Long saleId;

    @Column(name = "note")
    private String note;

    @Column(name = "assign_to")
    private String assignTo;

    @Column(name = "in_time")
    private Date inIime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "status")
    private Long status;

    @Column(name = "from_department")
    private Long fromDepartment;

    @Column(name = "is_order")
    private Long isOrder;
}
