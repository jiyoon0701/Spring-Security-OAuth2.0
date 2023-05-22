package com.cos.security1.service.Impl;


import com.cos.security1.config.auth.UserDetailsImpl;
import com.cos.security1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.jws.Oneway;

/*
 * Spring Security에서 User를 인증할때 사용하는 서비스클래스이다.
 * UserDetails 객체를 DB에서 가져오는 역할을 한다.
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService service;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsServiceImpl.loadUserByUsername :::: {}",username);

        UserDetailsImpl user = service.findByUsername(username);

        if(ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("Invalid username, please check user info !");
        }

        user.setAuthorities(AuthorityUtils.createAuthorityList(String.valueOf(user.getRole())));

        return user;
    }
}
