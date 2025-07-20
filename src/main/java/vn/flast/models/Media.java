package vn.flast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "media")
@Entity
@Getter @Setter
public class Media {

    public static final Integer NOT_ACTIVE = 0;

    public static final Integer ACTIVE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "object")
    private String object;

    @Column(name = "object_id")
    private Integer objectId;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "status")
    private Integer status ;
}
