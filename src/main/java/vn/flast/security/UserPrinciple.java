package vn.flast.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serial;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import vn.flast.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Objects;


public class UserPrinciple implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer id;
    private final String ssoId;
    private final String username;

    private String name;
    private final String email;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(
        Integer id,
        String ssoId,
        String email,
        String password,
        List<SimpleGrantedAuthority> authorities
    ) {
        this.id = id;
        this.ssoId = ssoId;
        this.username = ssoId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrinciple build(User user) {
        
        List<SimpleGrantedAuthority> authorities = user.getUserProfiles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getType())).toList();

        return new UserPrinciple(
            user.getId(),
            user.getSsoId(),
            user.getEmail(),
            user.getPassword(),
            authorities
        );
    }
    
    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSsoId() {
        return ssoId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}
