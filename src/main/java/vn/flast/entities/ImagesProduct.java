package vn.flast.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ImagesProduct {
    private Integer productid;
    private String fileName;
    private Boolean isSlideshow;
}
