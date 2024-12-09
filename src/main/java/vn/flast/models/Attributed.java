package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vn.flast.utils.NumberUtils;

@Table(name = "attributed")
@Entity
@Getter @Setter
public class Attributed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Name attributed not empty .!")
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "status")
    private Integer status;

    @PrePersist
    public void beforeInsert() {
        if(NumberUtils.isNull(status)) {
            status = 1;
        }
    }
}
