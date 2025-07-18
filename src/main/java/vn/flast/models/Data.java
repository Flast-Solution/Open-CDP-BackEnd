package vn.flast.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Table(name = "data")
@Entity
@Getter @Setter
public class Data implements Cloneable {

    public enum FROM_DEPARTMENT {
        FROM_DATA(0) {
            @Override
            public String label() {
                return "MQL";
            }
        },
        FROM_SALE(1) {
            @Override
            public String label() {
                return "SQL";
            }
        },
        FROM_RQL(2) {
            @Override
            public String label() {
                return "RQL";
            }
        };

        private final int value;
        public int value() {
            return value;
        }

        public abstract String label();
        FROM_DEPARTMENT(int value) {
            this.value = value;
        }

        public static HashMap<Integer, String> labels() {
            HashMap<Integer, String> labels = new HashMap<>();
            for (FROM_DEPARTMENT department : FROM_DEPARTMENT.values()) {
                labels.put(department.value, department.label());
            }
            return labels;
        }
    }

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
    private Integer source;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

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

    @Column(name = "sale_id")
    private Integer saleId;

    @Column(name = "note")
    private String note;

    @Column(name = "assign_to")
    private String assignTo;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "in_time")
    private Date inTime;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "from_department")
    private Integer fromDepartment;

    @Column(name = "is_order")
    private Long isOrder;

    @Column(name = "pre_sale_call")
    private Integer preSaleCall;

    @Column(name = "after_sale_call")
    private String afterSaleCall;

    @Transient
    private String csCause;

    @Transient
    private Date csTime;

    @Transient
    List<DataMedia> listFileUploads = new ArrayList<>();

    @Transient
    private List<String> fileUrls = new ArrayList<>();

    public enum PreSaleCall {
        CHUA_LIEN_HE(0),
        DA_LIEN_HE(1);
        private final int value;
        public int value() {
            return value;
        }
        PreSaleCall(int value) {
            this.value = value;
        }
    }

    public enum AfterSaleCall {
        CHUA_LIEN_HE("Chưa liên hệ"),
        KHONG_HAI_LONG("Không hài lòng"),
        HAI_LONG("Hài lòng"),
        RAT_HAI_LONG("Rất hài lòng");

        private final String value;
        public String value() {
            return value;
        }
        AfterSaleCall(String value) {
            this.value = value;
        }
    }

    @Override
    public Data clone() {
        try {
            return (Data) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
