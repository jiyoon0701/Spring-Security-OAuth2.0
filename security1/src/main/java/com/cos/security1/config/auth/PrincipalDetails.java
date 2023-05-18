package com.cos.security1.config.auth;
import com.cos.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어준다. (Security ContextHolder)
// 세션에 들어갈 수 있는 정보는 오브젝트 -> Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 함.
// User 오브젝트 타입 => UserDetail 타입 객체

// Security Session => Authentication => UserDetails
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; // 콤포지션
    private Map<String, Object> attributes; // 플랫폼을 통해 받은 정보들을 담는 Map

    // 일반 로그인
    public PrincipalDetails(User user){
        this.user = user;
    }

    // OAuth 로그인
    // 로그인을 통해 받은 정보들을 그대로 담아서 return
    public PrincipalDetails(User user, Map<String, Object> attributes){ // 생성자 오버로딩
        this.user = user;
        this.attributes = attributes;
    }

    // 해당 User의 권한을 리턴하는 곳!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
        collect.add(()->{ return user.getRole();});
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        // ex.
        // 우리 사이트는 1년동안 회원이 로그인을 안하면 휴면 계정으로 하기로 함
        // 현재 시간 - 로긴 시간 => 1년을 초과하면 휴면 계정

        return true;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    @Override
    public String getName() {
        return null;
    }
}
