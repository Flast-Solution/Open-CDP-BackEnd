package vn.flast.domains.material;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnitType {
    QUANTITY("quantity"),
    DIMENSION("dimension"),
    WEIGHT("weight");
    private final String value;
}
