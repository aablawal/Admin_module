package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class LogoutController {

    private final ConsumerTokenServices tokenServices;

    @PostMapping("/v1/logout/logout")
    public ResponseEntity<APIResponse> revokeToken(OAuth2Authentication auth) {

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        tokenServices.revokeToken(details.getTokenValue());

        return ResponseEntity.ok().body(new APIResponse("Logout successful",true,null));

    }

    @GetMapping("/v1/get_sessionId")
    public ResponseEntity<APIResponse<String>> getSessionId(OAuth2Authentication auth) {

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        return ResponseEntity.ok().body(new APIResponse("Logout successful",true,details.getSessionId()));

    }


}
