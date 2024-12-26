package vn.flast.entities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeInfo {

    private Integer id;
    private String ssoId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private Integer status;
}
