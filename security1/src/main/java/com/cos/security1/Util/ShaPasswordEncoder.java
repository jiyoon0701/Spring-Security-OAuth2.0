package com.cos.security1.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
 * 인증을 할때 Form에서 넘어온 비밀번호를 암호화하여
 * DB에서 불러온 암호화된 인증패스워드를 비교하는 역할을 한다.
 */
@Slf4j
@Component
public class ShaPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        log.info("ShaPasswordEncoder.encode :::: {}",rawPassword);
        return Crypto.sha256(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.info("ShaPasswordEncoder.matches :::: {}<->{}",rawPassword,encodedPassword);
        return Crypto.sha256(rawPassword.toString()).equals(encodedPassword);
    }
}
