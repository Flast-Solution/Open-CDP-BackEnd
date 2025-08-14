package vn.flast.models;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Table(name = "data_work")
@Entity
@Getter @Setter
public class DataWork {

    public static final Integer CHUA_SENT_T7_T30 = 0;
    public static final Integer DA_SENT_T7_T30 = 1;
    public static final Integer CHUA_THANH_DON_HANG = 0;
    public static final String ORDER_TYPE_CO_HOI = "cohoi";

    public static final Integer SET_PRIORITY_TO_ONE = 1;
    public static final Integer RESET_PRIORITY_TO_ZERO = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "data_id")
    private Integer dataId;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "sale_id")
    private Integer saleId;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "status_data")
    private Integer statusData;

    @Column(name = "status_order")
    private Integer statusOrder;

    @Column(name = "opportunited_at")
    private Date opportunitedAt;

    @Column(name = "doned_at")
    private Date donedAt;

    @UpdateTimestamp
    @Column(name = "in_time")
    private Date inTime;

    @CreationTimestamp
    @Column(name = "in_time_lead")
    private Date inTimeLead;

    @Column(name = "is_sent_t7")
    private Integer isSentT7;

    @Column(name = "is_sent_t30")
    private Integer isSentT30;
}
