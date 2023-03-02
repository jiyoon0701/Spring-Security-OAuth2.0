# Springboot-Security-Basic

### MySQL DB 및 사용자 생성

```sql
create user 'cos'@'%' identified by 'cos1234';
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
create database security;
use security;
```

### WebMvcConfig.java .Mustache를 .html로 인식할 수 있게 설정 방법

```java
MustacheViewResolver resolver = new MustacheViewResolver();

        resolver.setCharset("UTF-8");
        resolver.setContentType("text/html;charset=UTF-8"); // 내가 너한테 던지는 파일은 html이야
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html"); // .mustache여도 html로 인식

        registry.viewResolver(resolver); // registry를 뷰리졸브로 등록
```

### SecurityConfig.java 권한 설정 방법

```java
// protected void configure(HttpSecurity http) 함수 내부에 권한 설정법
http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // 로그인 해야만 접근가능
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 로그인과 권한이 어드민 or 매니저
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 로그인과 권한이 어드민만 접근 가능
                .anyRequest().permitAll() // 위에 3개 주소가 아니면 모든 권한은 허용
                .and() //권한이 없는 사용자가 들어올 시 로그인 페이지로 이동
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // login주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
                .defaultSuccessUrl("/");
```
 - 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
 - 로그인 진행이 완료가 되면 시큐리티 session을 만들어준다. (Security ContextHolder)
 - 세션에 들어갈 수 있는 정보는 오브젝트는 Authentication 타입 객체
 - Authentication 안에 User 정보가 있어야 함 -> 이때 User는 UserDetail 타입 객체
 - login 요청이 오면 자동으로 UserDetailsService 타입으로 IOC되어 있는 loadUserByUsername 함수가 발생

### 컨트롤러의 함수에 직접 권한 설정 하는 방법

```java
// 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화 SecurityConfig.java에 설정
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)

// 컨트롤러에 어노테이션 거는 법
@PostAuthorize("hasRole('ROLE_MANAGER')")
@PreAuthorize("hasRole('ROLE_MANAGER')")
@Secured("ROLE_MANAGER")
```
