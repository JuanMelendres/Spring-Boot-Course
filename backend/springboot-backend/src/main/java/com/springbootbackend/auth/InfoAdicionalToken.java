package com.springbootbackend.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.springbootbackend.models.entity.Users;
import com.springbootbackend.models.services.IUsersService;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

	@Autowired
	private IUsersService userService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		Users user = userService.findByuserName(authentication.getName());

		Map<String, Object> info = new HashMap<>();

		info.put("info_adicional", "Hi: ".concat(authentication.getName()));

		info.put("first_name", user.getFirstName());
		info.put("last_name", user.getLastName());
		info.put("email", user.getEmail());

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

		return accessToken;
	}

}
