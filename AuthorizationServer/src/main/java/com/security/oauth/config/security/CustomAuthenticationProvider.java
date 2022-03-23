package com.security.oauth.config.security;

import com.security.oauth.service.UserService;
import com.security.oauth.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserService userService;

	@Autowired
    private  PasswordEncoder passwordEncoder;

	@Override
	@SuppressWarnings("unchecked")
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String userName = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		User findUser = userService.findByName(userName);
		
		if(!passwordEncoder.matches(password, findUser.getPassword())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않음");
		}
		
		List<GrantedAuthority> authorityList = (List<GrantedAuthority>) findUser.getAuthorities();
        
        return new UsernamePasswordAuthenticationToken(userName, password, authorityList);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
    
}
