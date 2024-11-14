package vn.flast.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PriceRange {
    private Long start;
    private Long end;
    private Long price;
}
