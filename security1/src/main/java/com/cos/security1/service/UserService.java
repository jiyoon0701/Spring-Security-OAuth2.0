package com.cos.security1.service;

import com.cos.security1.config.auth.UserDetailsImpl;

public interface UserService {
    public UserDetailsImpl findByUsername(String username);
    public UserDetailsImpl save(UserDetailsImpl user);
}
