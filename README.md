# Spring-Security-OAuth2.0

### Authentcation 객체가 가질 수 있는 2가지 타입
- 시큐리티 세션에 들어갈 수 있는 타입은 Authentication 객체 
  - 시큐리티 세션에 Authentication 객체가 들어가있다면 로그인이 되어있는 상태를 의미
- Authentication에는 UserDetails와 OAuth2User 타입이 들어갈 수 있음
  - UserDetails -> General Login
  - OAuth2User -> OAuth Login
- General, OAuth를 컨트롤러에서 구분해서 받기가 어렵다
  - 해결법으로 XClass(PrincipalDetails)에서 UserDetails, OAuth2User 둘 다 implements로 상속 

### application.yml
```
  security:
    oauth2:
      client:
        registration:
          google: # /oauth2/authorization/google 이 주소를 동작하게 한다.
            client-id: {client-id}
            client-secret: {client-secret}
            scope:
              - email
              - profile

          facebook: # /oauth2/authorization/google 이 주소를 동작하게 한다.
            client-id: {client-id}
            client-secret: {client-secret}
            scope:
              - email
              - public_profile

          # 네이버는 OAuth2.0 공식 지원대상이 아니라서 provider 설정이 필요하다.
          # 요청주소도 다르고, 응답 데이터도 다르기 때문이다.
          naver: # /oauth2/authorization/google 이 주소를 동작하게 한다.
            client-id: {client-id}
            client-secret: {client-secret}
            scope:
              - name
              - email
            client-name: Naver # 클라이언트 이름은 구글, 페이스북도 대문자로 시작
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:8080/login/oauth2/code/naver

        provider:
          naver:
            authorization-uri: https://nid.naver.com/oauth2.0/authorize
            token-uri: https://nid.naver.com/oauth2.0/token
            user-info-uri: https://openapi.naver.com/v1/nid/me
            user-name-attribute: response # 회원정보를 json으로 받는데 response라는 키 값으로 네이버가 리턴해줌
```

