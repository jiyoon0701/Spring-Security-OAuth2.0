package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // IOC관리를 위해 어노테이션 추가
// View 랜더링을 위한 ViewController 설정이다.
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    //    WebMvcConfigurer.super.addViewControllers(registry);

        registry.addViewController("/loginPage")
                .setViewName("login");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        MustacheViewResolver resolver = new MustacheViewResolver();

        resolver.setCharset("UTF-8");
        resolver.setContentType("text/html;charset=UTF-8"); // 내가 너한테 던지는 파일은 html이야
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html"); // .mustache여도 html로 인식

        registry.viewResolver(resolver); // registry를 뷰리졸브로 등록
    }
}
