package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Table(name = "customer_persional")
@Entity
@Getter
@Setter
public class Data {

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

        public static HashMap<Integer, String> lables() {
            HashMap<Integer, String> labels = new HashMap<>();
            for (FROM_DEPARTMENT department : FROM_DEPARTMENT.values()) {
                labels.put(department.value, department.label());
            }
            return labels;
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

    public enum DataType {
        SANPHAM_DICHVU(0){
            @Override
            String label() {
                return "Sản phẩm dịch vụ";
            }
        },
        DOI_TAC(1){
            @Override
            String label() {
                return "Đối tác";
            }
        },
        KHIEU_NAI(2){
            @Override
            String label() {
                return "Khiếu nại";
            }
        };

        private final int value;
        public int value() {
            return value;
        }
        abstract String label();
        DataType(int value){
            this.value = value;
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
    private Integer status;

    @Column(name = "from_department")
    private Integer fromDepartment;

    @Column(name = "is_order")
    private Long isOrder;

    @Transient
    private List<String> fileUrls = new ArrayList<>();

}
