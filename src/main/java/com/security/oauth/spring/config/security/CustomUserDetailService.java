package com.security.oauth.spring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.oauth.auth.domain.User;
import com.security.oauth.auth.service.UserMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserMapper userMapper;
	
	private final AccountStatusUserDetailsChecker detailChecker = new AccountStatusUserDetailsChecker();
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findByName(username);
        
		if(user == null)
            throw new UsernameNotFoundException("유저 정보 찾을 수 없음");
        
		return user;
	}

	
}
