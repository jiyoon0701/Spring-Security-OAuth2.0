package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // View를 리턴!!
public class IndexController {

    // localhost:8080/
    // localhost:8080
    @GetMapping({ "", "/" })
    public String index() {
        // 머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정 : templates (prefix), .mustache(suffix) 생략 가능 !! -> 디펜던시가 알아서 경로를 잡아줌
        return "index"; // src/main/resources/templates/index.mustache를 잡기 때문에 변경해야함 -> config/WebMvcConfig.java

    }
}
