package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class LogoutController {

    @Resource(name = "tokenServices")
    private final ConsumerTokenServices tokenServices;

    @PostMapping("/v1/logout/logout")
    public ResponseEntity<APIResponse> revokeToken(OAuth2Authentication auth) {

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        tokenServices.revokeToken(details.getTokenValue());

        return ResponseEntity.ok().body(new APIResponse("Logout successful",true,null));

    }
}
