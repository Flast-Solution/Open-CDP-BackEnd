package vn.flast.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vn.flast.exception.ResourceNotFoundException;
import vn.flast.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Table(name = "customer_order")
@Entity
@Getter @Setter
public class CustomerOrder implements Cloneable {

    public static String TYPE_CO_HOI = "cohoi";
    public static String TYPE_ORDER = "order";

    public static int CLASSIFICATION_BAN_LE = 1;
    public static int CLASSIFICATION_SAN_XUAT = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "data_id")
    private Long dataId;

    @Column(name = "source")
    private Integer source;

    @Column(name = "enterprise_id")
    private Long enterpriseId;

    @Column(name = "enterprise_name")
    private String enterpriseName;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "code")
    private String code;

    @Column(name = "total_not_vat")
    private Double totalNotVat;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_receiver_name")
    private String customerReceiverName;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "customer_ward_id")
    private Integer customerWardId;

    @Column(name = "customer_province_id")
    private Integer customerProvinceId;

    @Column(name = "customer_mobile_phone")
    private String customerMobilePhone;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_note")
    private String customerNote;

    @Column(name = "discount_info")
    private String discountInfo;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "price_off")
    private Double priceOff;

    @Column(name = "voucher")
    private String voucher;

    @Column(name = "shipping_cost")
    private Integer shippingCost;

    @Column(name = "shipping_real")
    private Integer shippingReal;

    @Column(name = "cod_cost")
    private Integer codCost;

    @Column(name = "transport_type_id")
    private Integer transportTypeId;

    @Column(name = "total")
    private Double total;

    @Column(name = "vat")
    private Integer vat = 0;

    @Column(name = "fee_import")
    private Long feeImport;

    @Column(name = "paid")
    private Double paid;

    @Column(name = "fee_sale_other", columnDefinition = "integer default 0")
    private Integer feeSaleOther = 0;

    @Column(name = "shipping_status")
    private Long shippingStatus;

    @Column(name = "cancel_at")
    private Date cancelAt;

    @Column(name = "done_at")
    private Date doneAt;

    @Column(name = "user_create_id")
    private Integer userCreateId;

    @Column(name = "user_create_username")
    private String userCreateUsername;

    @Column(name = "faulty")
    private Integer faulty;

    @Column(name = "status")
    private Integer status;

    @Column(name = "type")
    private String type;

    @Column(name = "opportunity_at")
    private Date opportunityAt;

    @Column(name = "classification")
    private Integer classification = CLASSIFICATION_SAN_XUAT;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @JsonManagedReference(value = "details")
    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<CustomerOrderDetail> details;

    public CustomerOrderDetail takeDetailByCode(String dtCode) {
        return this.getDetails()
            .stream()
            .filter(detail -> detail.getCode().equals(dtCode))
            .findFirst()
            .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerOrder clone() {
        try {
            return (CustomerOrder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public CustomerOrder cloneNoDetail() {
        try {
            CustomerOrder newOrder = (CustomerOrder) super.clone();
            newOrder.setDetails(new ArrayList<>());
            return newOrder;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @PrePersist
    public void beforeSave() {
        if(NumberUtils.isNull(status)) {
            status = 0;
        }
    }

    public boolean isOrder() {
        return this.type.equals(TYPE_ORDER);
    }
}
