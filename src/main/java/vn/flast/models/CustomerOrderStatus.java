package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.flast.utils.NumberUtils;

@Table(name = "customer_order_status")
@Entity
@Getter @Setter
public class CustomerOrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "`order`")
    private Integer order;

    @Column(name = "del_flag")
    private Integer delFlag;

    @Column(name = "status")
    private Integer status;

    @PrePersist @PreUpdate
    public void updateDefault() {
        if(NumberUtils.isNull(order)) {
            order = 0;
        }
        if(NumberUtils.isNull(delFlag)) {
            delFlag = 0;
        }
    }
}
