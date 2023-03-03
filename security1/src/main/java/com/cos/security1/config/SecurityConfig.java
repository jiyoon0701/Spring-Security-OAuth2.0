package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 활성화 -> 스프링 시큐리티 필터(현재 파일)가 스프링 필터체인(기본 필터체인)에 등록된다
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, prePostEnabled는 preAuthorize, postAuthorize 어노테이션을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 해당 메스드의 리턴되는 오브젝트를 Ioc로 등록해준다
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 로그인 해야만 접근가능
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 로그인과 권한이 어드민 or 매니저
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 로그인과 권한이 어드민만 접근 가능
                .anyRequest().permitAll() // 위에 3개 주소가 아니면 모든 권한은 허용
                .and() //권한이 없는 사용자가 들어올 시 로그인 페이지로 이동
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // login주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm"); // 구글 로그인이 완료된 뒤의 후처리가 필요함
    }
}
