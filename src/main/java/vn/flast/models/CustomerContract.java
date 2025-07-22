package vn.flast.models;

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
