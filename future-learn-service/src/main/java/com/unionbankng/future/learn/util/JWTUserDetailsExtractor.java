package com.unionbankng.future.learn.util;

import com.unionbankng.future.learn.pojo.JwtUserDetail;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;

public class JWTUserDetailsExtractor {

    static Logger logger = LoggerFactory.getLogger(JWTUserDetailsExtractor.class);

    public static JwtUserDetail getUserDetailsFromAuthentication(Principal principal){

        logger.info("Principal is ================== : {}", principal.getName());
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;

        logger.info("Account is ================== : {}", keycloakAuthenticationToken.getAccount().toString());
        logger.info("context is ================== : {}", keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().toString());
        logger.info("token is ================== : {}", keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken());
        AccessToken accessToken = keycloakAuthenticationToken
                .getAccount()
                .getKeycloakSecurityContext()
                .getToken();

//        String userImg = detailsMap.get("userImg") == null ? null : detailsMap.get("userImg").toString();

        return JwtUserDetail.builder().userEmail(accessToken.getEmail())
                .userFullName(accessToken.getName()).userUUID(accessToken.getSubject()).build();


    }

    public static String getAccessTokenString(Principal principal){

        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        return keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getTokenString();



    }


}
