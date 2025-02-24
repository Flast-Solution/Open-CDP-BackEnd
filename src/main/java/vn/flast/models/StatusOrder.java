package vn.flast.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "status_order")
@Entity
@Getter
@Setter
public class StatusOrder {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private Integer status;
}
