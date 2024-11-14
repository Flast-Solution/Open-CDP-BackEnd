package vn.flast.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PriceRange {
    private Integer start;
    private Integer end;
    private Double price;
}
