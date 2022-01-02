package com.security.oauth.auth.service;

import org.apache.ibatis.annotations.Mapper;

import com.security.oauth.auth.domain.User;

@Mapper
public interface UserMapper {

	public User findByName(String name);
}
