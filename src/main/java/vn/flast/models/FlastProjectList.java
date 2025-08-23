package vn.flast.models;
/**************************************************************************/
/*  FlastProjectInit.java                                                 */
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
/*************************************************************************/

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vn.flast.utils.JsonUtils;
import vn.flast.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "flast_projects_list")
@Entity
@Getter @Setter
public class FlastProjectList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "status")
    private String status;

    @Column(name = "budget")
    private Double budget;

    @Column(name = "priority")
    private String priority;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "manager_id")
    private Integer managerId;

    @JsonIgnore
    @Column(name = "members")
    private String members;

    @Column(name = "progress")
    private Integer progress;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    public void beforeSave() {
        if(StringUtils.isNull(status)) {
            status = "Not Started";
        }
    }

    @Transient
    List<String> listMember = new ArrayList<>();
    public void fitMEmber() {
        listMember = JsonUtils.Json2ListObject(members, String.class);
    }
}
