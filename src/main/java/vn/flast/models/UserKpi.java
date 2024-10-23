package vn.flast.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "user_kpi")
@Entity
@Getter
@Setter
public class UserKpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "department")
    private Long department;

    @Column(name = "type")
    private String type;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "kpi_total")
    private Long kpiTotal;

    @Column(name = "kpi_revenue")
    private Long kpiRevenue;

    @Column(name = "month")
    private Long month;

    @Column(name = "year")
    private Long year;
}
