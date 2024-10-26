package vn.flast.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter @Setter
public class User {

    public static final String RULE_ADMIN = "ADMIN";
    public static final String RULE_PERCHARING = "PERCHARGING";
    public static final String RULE_KHO = "WAREHOUSE";

    public static final String RULE_MANAGER = "SALE_MANAGER";
    public static final String RULE_SALE_LEADER = "SALE_LEADER";
    public static final String RULE_SALE_MENBER = "SALE";
    public static final String RULE_PARTNER = "PARTNER";
    public static final String RULE_CSKH = "DBA";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "sso_id", nullable = false)
    private String ssoId;

    @NotNull
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Lob
    @Column(name = "token")
    private String fireBaseToken;

    @Column(name = "layout")
    private String layout;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "status")
    private Integer status;

    @Transient
    private Set<UserProfile> userProfiles = new HashSet<>();

    public boolean ruleCskh() {
        return this.checkRule(RULE_CSKH);
    }

    @JsonIgnore
    @Transient
    public boolean checkRule(String rule) {
        if(this.userProfiles.isEmpty()) {
            return false;
        }
        for(UserProfile userProfile : this.userProfiles) {
            if(userProfile.getType().contains(rule)) {
                return true;
            }
        }
        return false;
    }
}
