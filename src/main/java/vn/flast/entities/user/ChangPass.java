package vn.flast.entities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangPass {
    private String oldPass;
    private String newPass;
    private Integer uId;
}
