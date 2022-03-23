package com.security.oauth.config.security;

import com.security.oauth.user.domain.User;
import com.security.oauth.user.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
