package com.cos.security1.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Spring Security에서 로그인 페이지로 리다이렉트 시켜줄 Entrypoint객체이다.
 * 만약 권한이 없는 사용자가 페이지에 접근하였을 때, 해당 객체가 로그인 페이지로
 * 리다이렉트 시켜주는 역할을 담당한다.
 */
@Slf4j
public class CustomAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public CustomAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.info("CustomAuthenticationEntryPoint.commence :::: {}",request.getRequestURI());
        super.commence(request, response, authException);
    }
}
