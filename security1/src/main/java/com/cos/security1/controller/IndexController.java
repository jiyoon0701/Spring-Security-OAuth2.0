package com.cos.security1.controller;

import com.cos.security1.config.SecurityConfig;
import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴!!
public class IndexController {

    @Autowired
    private UserRepository userRepository;

 //   @Autowired
   // private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login") // UserDetails -> PrincipalDetails도 가능
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) { // DI(의존성 주입)
        System.out.println("/test/login");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        //System.out.println("authentication : " + authentication.getPrincipal());
        System.out.println("authentication : " + principalDetails.getUser());
        //System.out.println("userDetails : " + principalDetails.getUsername());
        System.out.println("userDetails : " + userDetails.getUsername());
        return "세션 정보 확인";
    }

    @GetMapping("/test/oauth/login") // UserDetails -> PrincipalDetails도 가능
    public @ResponseBody String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) { // DI(의존성 주입)
        System.out.println("/test/oauth/login");
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication : " + oauth2User.getAttributes()); // Authentication으로도 접근이 가능하고
        System.out.println("oauth2User:" + oauth.getAttributes()); // 어노테이션으로도 접근이 가능하다
        return "OAuth 세션 정보 확인";
    }

    // localhost:8080/
    // localhost:8080
    @GetMapping({ "", "/" })
    public String index() {
        // 머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정 : templates (prefix), .mustache(suffix) 생략 가능 !! -> 디펜던시가 알아서 경로를 잡아줌
        return "index"; // src/main/resources/templates/index.mustache를 잡기 때문에 변경해야함 -> config/WebMvcConfig.java
    }

    // OAuth 로그인을 해도 PrincipalDetails
    // 일반 로그인을 해도 PrincipalDetails
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails: " + principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/login")
    public String lo() {
        return "login";
    }


    // 스프링시큐리티 해당주소를 낚아채버린다! -> SecurityConfig파일 생성 후 작동안함.
    @GetMapping("/loginForm")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
       // String encPassword = bCryptPasswordEncoder.encode(rawPassword);
     //  user.setPassword(encPassword);
        //userRepository.save(user); // 회원가입 잘 됨 . 비밀번호 : 1234 => 시큐리티로 로그인을 할 수 없음 -> 패스워드가 암호화가 안되었기 때문이다.
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN") // 간단하게 메소드 하나에만 권한을 걸 때 사용함
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // data 메소드가 실행되기 직전에 실행되는 어노테이션
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터 정보";
    }
}
