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

@Table(name = "customer_order")
@Entity
@Getter
@Setter
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "data_id")
    private Long dataId;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "channel_id")
    private Long channelId;

    @Column(name = "enterprise_id")
    private Long enterpriseId;

    @Column(name = "enterprise_name")
    private String enterpriseName;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "code")
    private String code;

    @Column(name = "total_not_vat")
    private Long totalNotVat;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_receiver_name")
    private String customerReceiverName;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_ward_id")
    private Long customerWardId;

    @Column(name = "customer_district_id")
    private Long customerDistrictId;

    @Column(name = "customer_province_id")
    private Long customerProvinceId;


    @Column(name = "customer_mobile_phone")
    private String customerMobilePhone;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_note")
    private String customerNote;

    @Column(name = "discount_info")
    private String discountInfo;

    @Column(name = "subtotal")
    private Long subtotal;

    @Column(name = "price_off")
    private Long priceOff;

    @Column(name = "voucher")
    private String voucher;

    @Column(name = "shipping_cost")
    private Long shippingCost;

    @Column(name = "shipping_real")
    private Long shippingReal;

    @Column(name = "cod_cost")
    private Long codCost;

    @Column(name = "transport_type_id")
    private Long transportTypeId;

    @Column(name = "total")
    private Long total;

    @Column(name = "vat")
    private Long vat;

    @Column(name = "fee_import")
    private Long feeImport;

    @Column(name = "paid")
    private Long paid;

    @Column(name = "flag_freeship")
    private String flagFreeship;

    @Column(name = "shipping_status")
    private Long shippingStatus;

    @Column(name = "payment_status")
    private Long paymentStatus;

    @Column(name = "cancel_at")
    private Date cancelAt;

    @Column(name = "paid_time")
    private Date paidTime;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "done_at")
    private Long doneAt;

    @Column(name = "user_create_id")
    private Long userCreateId;

    @Column(name = "user_create_username")
    private String userCreateUsername;

    @Column(name = "source")
    private Long source;

    @Column(name = "faulty")
    private Long faulty;

    @Column(name = "status")
    private Long status;

    @Column(name = "type")
    private Long type;

    @Column(name = "opportunity_at")
    private Date opportunityAt;


}
