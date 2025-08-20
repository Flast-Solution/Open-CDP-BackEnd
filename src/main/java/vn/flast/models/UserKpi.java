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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.flast.entities.Kpi;
import vn.flast.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

@Table(name = "user_kpi")
@Entity
@Getter @Setter
public class UserKpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @Column(name = "content")
    private String content;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "year")
    private Long year;

    @Column(name = "month")
    private Long month;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Kpi> listKpi = new ArrayList<>();

    public void assignKPI() {
        listKpi = JsonUtils.Json2ListObject(content, Kpi.class);
    }
}
