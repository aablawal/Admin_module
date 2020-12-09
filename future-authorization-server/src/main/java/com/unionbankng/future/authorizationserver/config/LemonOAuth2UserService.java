package com.unionbankng.future.authorizationserver.config;

import java.io.Serializable;
import java.util.Map;

import com.unionbankng.future.authorizationserver.security.FutureDAOUserDetails;
import com.unionbankng.future.authorizationserver.security.FutureDAOUserDetailsService;
import com.unionbankng.future.authorizationserver.utils.Oauth2Utils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


/**
 * Logs in or registers a user after OAuth2 SignIn/Up
 */

public class LemonOAuth2UserService extends DefaultOAuth2UserService {

	private static final Log log = LogFactory.getLog(LemonOAuth2UserService.class);

	private FutureDAOUserDetailsService userDetailsService;
	private Oauth2Utils oauth2Utils;


	public LemonOAuth2UserService(FutureDAOUserDetailsService futureDAOUserDetailsService){
		this.userDetailsService = futureDAOUserDetailsService;
		this.oauth2Utils = new Oauth2Utils();
	}


	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oath2User = super.loadUser(userRequest);
		return buildPrincipal(oath2User, userRequest.getClientRegistration().getRegistrationId());
	}

	/**
	 * Builds the security principal from the given userReqest.
	 * Registers the user if not already reqistered
	 */
	public FutureDAOUserDetails buildPrincipal(OAuth2User oath2User, String registrationId) {

		Map<String, Object> attributes = oath2User.getAttributes();
		String email = oauth2Utils.getOAuth2Email(registrationId, attributes);
		boolean emailVerified = oauth2Utils.getOAuth2AccountVerified(registrationId, attributes);

    	FutureDAOUserDetails user = (FutureDAOUserDetails) userDetailsService.loadUserByUsername(email);



		return user;
	}
}