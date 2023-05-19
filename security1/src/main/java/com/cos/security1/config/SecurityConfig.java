package com.cos.security1.config;

import com.cos.security1.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
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
                .loginPage("/loginForm") // 구글 로그인이 완료된 뒤의 사후 처리가 필요함
                /*
                1. 코드 받기(인증 -> 구글에 로그인이 된 정상적인 사용자)
                2. 엑세스토큰(권한)
                3. 사용자 프로필 정보를 가져오고
                4. 정보를 토대로 회원가입을 자동으로 진행시킴
                4-2. 구글이 가진 이메일, 전화번호, 이름, 아이디 외에 정보가 필요할 때 ? 쇼핑몰을 한다면? -> 집주소, 백화점몰 -> vip, 일반 등급 등 추가 정보가 필요하다면 추가적인 회원가입이 진행되어야한다.

                TIP : 구글 로그인이 완료되면 코드 X, 엑세스 토큰 + 사용자 프로필 정보를 한번에 받는다.

                */

                 /*
                 oauth2Login에 성공하면 customOAuth2UserService에서 설정을 진행하겠다는 의미
                 */
                .userInfoEndpoint() // 로그인 성공 후 사용자 정보를 가져온다.
                .userService(principalOauth2UserService); // 후 처리  (사용자 정보를 처리할 때 사용)


    }
}
