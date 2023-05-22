package com.cos.security1.config.auth;

import com.cos.security1.model.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
@Entity
// * Spring Security에서 사용자를 인증할때 사용되는 객체이다.(DB에서 관리되는 객체)
// * UserDetailsService에서 사용자를 load할때 반환되는 결과객체이다.
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = -4608347932140057654L;

    @Id
    private Long id;
    private String username;
    private String password;
    private UserRole role;
    @Column(length=2000)
    private String access_token;
    private LocalDateTime access_token_validity;
    @Column(length=2000)
    private String refresh_token;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;
    @Transient
    private boolean accountNonExpired = true;
    @Transient
    private boolean accountNonLocked = true;
    @Transient
    private boolean credentialsNonExpired = true;
    @Transient
    private boolean enabled = true;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
