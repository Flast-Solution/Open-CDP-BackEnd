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

@Table(name = "attributed_value")
@Entity
@Getter @Setter
public class AttributedValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "attributed_id", nullable = false)
    @NotNull(message = "Name attributed not empty .!")
    private Integer attributedId;

    @Column(name = "value")
    private String value;

    @Column(name = "status")
    private Integer status;

    @PrePersist
    public void beforeInsert() {
        if(NumberUtils.isNull(status)) {
            status = 1;
        }
    }
}
