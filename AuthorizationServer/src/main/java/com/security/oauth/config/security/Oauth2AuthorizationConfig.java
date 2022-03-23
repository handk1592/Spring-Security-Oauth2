package com.security.oauth.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

@AllArgsConstructor
@Configuration
@EnableAuthorizationServer // OAuth2 권한 서버
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
	
	private AuthenticationManager authenticationManager;
	private DataSource dataSource;
	private PasswordEncoder passwordEncoder;
	private CustomUserDetailService customUserDetailService;
	
	// Oauth2 서버가 작동하기 위한 Endpoint 대한 정보 설정
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore())
			.authenticationManager(authenticationManager)
			.accessTokenConverter(jwtAccessTokenConverter())
			.userDetailsService(customUserDetailService);
	}
	

	/* 클라이언트 대한 정보를 설정하는 부분 */
    /* jdbc(DataBase)를 이용하는 방식 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("my-oauth-jwt.jks"), "mypassword".toCharArray());

		jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("my-oauth-jwt"));
		// jwtAccessTokenConverter.setSigningKey("jwtKey");

		return jwtAccessTokenConverter;
	}
	
	
//	@Override
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		clients.inMemory()
//				.withClient("clientId") 	// 클라이언트 아이디
//				.secret("{noop}clientSecret") 		// 클라이언트 시크릿
//				.redirectUris("http://localhost:8081/oauth2/callback")
//				.authorizedGrantTypes("authorization_code", "password")
//				.scopes("read", "write") 	// 해당 클라이언트의 접근 범위
//				.accessTokenValiditySeconds(60 * 60 * 4) // access token 유효 기간 (초 단위)
//				.refreshTokenValiditySeconds(60 * 60 * 24 * 120) // refresh token 유효 기간 (초 단위)
//		 		.autoApprove(true);   // OAuth Approval 화면 나오지 않게 처리
//	}

	
}
