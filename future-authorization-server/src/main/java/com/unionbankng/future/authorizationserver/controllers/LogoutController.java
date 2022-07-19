package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.ActivityLog;
import com.unionbankng.future.authorizationserver.pojos.JwtUserDetail;
import com.unionbankng.future.authorizationserver.utils.AppLogger;
import com.unionbankng.future.authorizationserver.utils.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class LogoutController {

    private final ConsumerTokenServices tokenServices;

    private final AppLogger appLogger;
    @PostMapping("/v1/logout/logout")
    public ResponseEntity<APIResponse> revokeToken(OAuth2Authentication auth) {

        //Get details of user that wants to logout
        JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(auth);


        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        tokenServices.revokeToken(details.getTokenValue());

        try {
            //############### Activity Logging ##########
            ActivityLog log = new ActivityLog();
            log.setDescription("User logged out");
            log.setUsername("Username: " + currentUser.getUserFullName() + "Email: " + currentUser.getUserEmail());
            log.setUserId("User ID: " + currentUser.getUserUUID());
            log.setDate("Date and Time: " + new Date());
            appLogger.log(log);
            //#########################################
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.ok().body(new APIResponse("Logout successful",true,null));

    }

//    @GetMapping("/v1/get_sessionId")
//    public ResponseEntity<APIResponse<String>> getSessionId(OAuth2Authentication auth) {
//
//        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
//        return ResponseEntity.ok().body(new APIResponse("Logout successful",true,details.getSessionId()));
//
//    }


}
