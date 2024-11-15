package vn.flast.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SkuAttributed {
    private Integer id;
    private Long attributedId;
    private Long attributedValueId;
}
