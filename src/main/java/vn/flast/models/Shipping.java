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

@Table(name = "shipping")
@Entity
@Getter
@Setter
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "transporter_id")
    private Long transporterId;

    @Column(name = "transporter_name")
    private String transporterName;

    @Column(name = "transporter_code")
    private String transporterCode;

    @Column(name = "fee")
    private String fee;

    @Column(name = "cod")
    private Long cod;

    @Column(name = "quality")
    private Long quality;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "ward_id")
    private Long wardId;

    @Column(name = "address")
    private String address;

    @Column(name = "in_time")
    private Date inTime;

    @Column(name = "status")
    private String status;
}
