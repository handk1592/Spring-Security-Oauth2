package com.security.oauth.auth.service;

import org.springframework.stereotype.Service;

import com.security.oauth.auth.domain.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserMapper {

    private UserMapper userMapper;

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
}
