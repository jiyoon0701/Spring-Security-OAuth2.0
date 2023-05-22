//package com.cos.security1.config.oauth;
//
//import com.cos.security1.config.auth.PrincipalDetails;
//import com.cos.security1.config.oauth.provider.FacebookUserInfo;
//import com.cos.security1.config.oauth.provider.GoogleUserInfo;
//import com.cos.security1.config.oauth.provider.NaverUserInfo;
//import com.cos.security1.config.oauth.provider.OAuth2UserInfo;
//import com.cos.security1.model.User;
//import com.cos.security1.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.Map;
//
//@Service
//public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
//    // DefaultOAuth2UserService는 OAuth2UserService의 구현체
//    // DefaultOauth2UserService는 OAuth2 로그인 시 loadUserByUsername메서드로 로그인한 유저가 DB에 저장되어 있는지 찾는다.
//    // OAuth2로 로그인한 사용자는 회원가입을 거치지 않기 때문에 DB에 유저가 없다면 회원가입 처리
//    // 있다면 Authentication를 반환하여 SecurityContextHolder에 저장할 수 있게 한다. ??
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    // 구글로 부터 받은 userReequest데이터에 대한 후 처리되는 함수
//    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException { // 후 처리 진행
//        // userRequest는 application.yml에서 설정한 정보들도 담겨져있을 것이다.
//        // 해당 클래스에서 userRequest 정보를 빼낼 수 있다.
//
//        // System.out.println("getClientRegistration:" +userRequest.getClientRegistration());
//        // 코드가 아닌 액세스 토큰와 사용자 프로필 정보를 한번에 불러온다.
//        // registrationID로 어떤 OAuth로 로그인을 구별
//
//         System.out.println("getAccessToken:" +userRequest.getAccessToken());
//    //     JsonNode accessToken = getGoogleUserInfo(userRequest.getAccessToken().toString());
//      //   System.out.println(accessToken.asText());
//
//
//        // System.out.println("getAttributes:" +super.loadUser(userRequest).getAttributes());
//
//        OAuth2User oauth2User = super.loadUser(userRequest);
//        // 구글로그인 버튼 -> 구글 로그인 창 -> 로그인 완료 -> CODE 리턴 (OAuth-Client라이브러리) -. AccessToken 요청
//        // userRequest 정보 -> loadUser함수 호출-> 구글로부터 회원 프로필 받아준다
//         System.out.println("getAttributes:" + oauth2User.getAttributes());
//
//        // 20230519
//        /*
//            Token값을 갖고 email 정보를 받아오는 로직 알아보기
//        * */
//
//        // 회원가입을 강제로 진행
//        OAuth2UserInfo oAuth2UserInfo = null;
//        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
//            oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
//        }else if((userRequest.getClientRegistration().getRegistrationId().equals("facebook"))){
//            oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
//        }else if ((userRequest.getClientRegistration().getRegistrationId().equals("naver"))){
//            oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
//            System.out.println("naver:" + oAuth2UserInfo);
//        }
//        else {
//            System.out.println("우리는 구글과 페이스북, 네이버만 지원해요");
//        }
//
////        String provider = userRequest.getClientRegistration().getRegistrationId(); // google
////        String providerId = oauth2User.getAttribute("sub"); // google에서는 sub, facebook은 id
////        String username = provider+"_"+providerId;
////        String password = bCryptPasswordEncoder.encode("겟인데어");
////        String email = oauth2User.getAttribute("email");
////        String role = "ROLE_USER";
//
//        String provider = oAuth2UserInfo.getProvider(); // google
//        String providerId = oAuth2UserInfo.getProviderId(); // google에서는 sub, facebook은 id
//        String username = provider+"_"+providerId;
//        String password = bCryptPasswordEncoder.encode("겟인데어");
//        String email = oAuth2UserInfo.getEmail();
//        String role = "ROLE_USER";
//
//
//
//        // 이미 회원가입이 되어있다면 ?
//        User userEntity = userRepository.findByUsername(username);
//
//        if(userEntity == null) {
//            userEntity = User.builder()
//                    .username(username)
//                    .password(password)
//                    .email(email)
//                    .role(role)
//                    .provider(provider)
//                    .providerId(providerId)
//                    .build();
//            userRepository.save(userEntity);
//        }
//
//
//        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
//    }
//}
