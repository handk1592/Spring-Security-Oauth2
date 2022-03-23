package com.security.oauth.user.domain;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class OauthVO {

	private String access_token;
	private String token_type;
	private int expires_in;
	private String scope;
	
}
