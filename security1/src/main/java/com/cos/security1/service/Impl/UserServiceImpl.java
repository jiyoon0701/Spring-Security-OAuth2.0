package com.cos.security1.service.Impl;

import com.cos.security1.config.auth.UserDetailsImpl;
import com.cos.security1.repository.UserRepository;
import com.cos.security1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetailsImpl findByUsername(String username) {
        log.info("UserServiceImpl.findByUsername :::: {}",username);
        return repository.findByUsername(username);
    }

    @Override
    public UserDetailsImpl save(UserDetailsImpl user) {
        log.info("UserServiceImpl.save :::: {}",user.toString());
        return repository.save(user);
    }
}
