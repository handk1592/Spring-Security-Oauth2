package com.security.oauth.controller;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth2")
public class OauthController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	private final RestTemplate restTemplate;
	
	@GetMapping(value = "/callback")
	public String callback(@RequestParam(name = "code") String authorizeCode) {
		logger.info("Request param cde = " + authorizeCode);

        String credentials = "clientId:clientSecret";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizeCode);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://localhost:8081/oauth2/callback");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8081/oauth/token", request, String.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            JsonObject JsonObject = gson.fromJson(response.getBody(), JsonObject.class);
        	
            logger.info(JsonObject.toString());
            
            return JsonObject.toString();
        }else {
        	logger.info(response.getStatusCode().toString());
        }
        
        return "";
	}

}
