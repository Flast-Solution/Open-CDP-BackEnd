package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Entity
@Table(name = "data_collection")
@NoArgsConstructor
@Getter @Setter
public class DataCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "service")
    private Integer service;

    @Column(name = "profile_id")
    private String profileId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "profile_link")
    private String profileLink;

    @Column(name = "address")
    private String address;

    @Column(name = "image_src")
    private String imageSrc;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "profile_type")
    private String profileType;

    @UpdateTimestamp
    @Column(name = "receive_time_new")
    private Date receiveTime;

    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "number")
    private String number;

    @Column(name = "channel")
    private Integer channel;

    @Column(name = "product")
    private Integer product;

    @Column(name = "email")
    private String email;

    @Column(name = "note")
    private String note;
}
