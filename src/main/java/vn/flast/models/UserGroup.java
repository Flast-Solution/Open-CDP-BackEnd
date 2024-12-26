package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "user_group")
@Getter
@Setter
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "member_number")
    private Integer memberNumber;

    @Column(name = "member_list")
    private String memberList;

    @Column(name = "leader_name")
    private String leaderName;

    @Column(name = "leader_id")
    private Integer leaderId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "in_time")
    private Date inTime;

    @Column(name = "status")
    private Integer status;


}
