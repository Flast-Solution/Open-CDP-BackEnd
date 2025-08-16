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




import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.util.Date;

public class PayOrder {

    @Serial
    private static final long serialVersionUID = 1L;
    public static final int LIMIT = 10;
    public static int STATUS_NOT_CONFIRM = 0;
    public static int STATUS_IS_CONFIRM = 1;

    private static final int METHOD_CHUYEN_KHOAN = 1;
    private static final int METHOD_COD = 2;
    private static final int METHOD_VI_MOMO = 3;
    public static final int METHOD_VI_VNPAY = 4;
    private static final int METHOD_THU_HO = 5;
    private static final int METHOD_TIEN_MAT = 6;

    public enum DEPARTMENT {
        sale, warehouse
    }


    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_code", length = 100)
    private String orderCode;

    @Column(name = "monney")
    private Long monney;

    @Column(name = "sale_id")
    private Integer saleId;

    @Column(name = "cause")
    private String cause;

    @Column(name = "method")
    private Integer method = METHOD_CHUYEN_KHOAN;

    @Column(name = "is_confirm")
    private Integer isConfirm;

    @Column(name = "content", length = 65535)
    private String content;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "in_time", length = 19)
    private Date inTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "confirm_time", length = 19)
    private Date confirmTime;

    @Column(name = "department", length = 100)
    @Enumerated(EnumType.STRING)
    private DEPARTMENT department = DEPARTMENT.sale;

    @Column(name = "source", length = 100)
    private String source;
}
