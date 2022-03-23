package com.security.oauth.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.security.oauth.user.domain.User;

@Mapper
public interface UserMapper {

	public User findByName(String name);
}
