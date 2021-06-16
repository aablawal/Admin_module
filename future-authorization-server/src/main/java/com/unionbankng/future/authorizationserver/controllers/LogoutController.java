package com.unionbankng.future.authorizationserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class LogoutController {

//    private final ConsumerTokenServices tokenServices;
//
//    @PostMapping("/v1/logout/logout")
//    public ResponseEntity<APIResponse> revokeToken(OAuth2Authentication auth) {
//
//        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
//        tokenServices.revokeToken(details.getTokenValue());
//
//        return ResponseEntity.ok().body(new APIResponse("Logout successful",true,null));
//
//    }
//
//    @GetMapping("/v1/get_sessionId")
//    public ResponseEntity<APIResponse<String>> getSessionId(OAuth2Authentication auth) {
//
//        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
//        return ResponseEntity.ok().body(new APIResponse("Logout successful",true,details.getSessionId()));
//
//    }


}
