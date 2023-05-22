package com.cos.secuccrity1.model;

import lombok.Data;

//  동적클라이언트 등록시 컨트롤러에서 매개변수로 사용되는 Dto클래스
@Data
public class RegisterClientInfo {
    private String name;
    private String redirectUri;
    private String clientType;
}
