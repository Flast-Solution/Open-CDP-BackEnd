package vn.flast.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "customer_tags")
@Entity
@Getter @Setter
public class CustomerTags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "tag")
    private String tag;
}
