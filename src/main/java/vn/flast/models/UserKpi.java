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

@Table(name = "user_kpi")
@Entity
@Getter
@Setter
public class UserKpi {

    public static final int DEPARTMENT_SALE = 0;
    public static final int DEPARTMENT_MARKETTING = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "department")
    private Long department;

    @Column(name = "type")
    private String type;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "kpi_total")
    private Integer kpiTotal;

    @Column(name = "kpi_revenue")
    private Integer kpiRevenue;

    @Column(name = "month")
    private Long month;

    @Column(name = "year")
    private Long year;

    @Column(name = "fee")
    private Integer fee = 0;

    @Column(name = "list_fee")
    private String listFee;

    public enum KPI_TYPE_SALE {
        FROM_DATA(0),
        FROM_SALE(1);
        private final int value;
        public int value() {
            return value;
        }
        KPI_TYPE_SALE(int value) {
            this.value = value;
        }
    }

    public enum KPI_TYPE_MARKETING {
        OF_LEAD(0),
        OF_TRAFIX(1);
        private final int value;
        public int value() {
            return value;
        }
        KPI_TYPE_MARKETING(int value) {
            this.value = value;
        }
    }
}
