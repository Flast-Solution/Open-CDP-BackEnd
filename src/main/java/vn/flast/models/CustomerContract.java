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
import vn.flast.config.ConfigUtil;
import vn.flast.entities.FilesInterface;

import java.util.Date;
import java.util.List;

@Table(name = "customer_contract")
@Entity
@Getter @Setter
public class CustomerContract implements FilesInterface {

    public static String FOLDER_UPLOAD = "/uploads/enterprise/";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "enterprise_id")
    private Long enterpriseId;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "file_name")
    private String fileName;

    @CreationTimestamp
    @Column(name = "in_time")
    private Date inTime;

    public String createUrlFile() {
        return ConfigUtil.HOST_URL + fileName;
    }

    @Override
    public String createFolderUpload() {
        return FOLDER_UPLOAD;
    }

    @Override
    public List<String> filePass() {
        return List.of("doc", "pdf");
    }
}
