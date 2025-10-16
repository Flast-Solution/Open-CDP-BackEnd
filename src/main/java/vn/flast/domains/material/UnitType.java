package vn.flast.domains.material;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnitType {
    QUANTITY("QUANTITY"),
    DIMENSION("DIMENSION"),
    WEIGHT("WEIGHT");
    private final String value;
}
