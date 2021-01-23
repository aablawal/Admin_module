package com.unionbankng.future.learn.util;

import com.unionbankng.future.learn.pojo.JwtUserDetail;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import java.security.Principal;

public class JWTUserDetailsExtractor {

    public static JwtUserDetail getUserDetailsFromAuthentication(Principal principal){


        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();

//        String userImg = detailsMap.get("userImg") == null ? null : detailsMap.get("userImg").toString();

        return JwtUserDetail.builder().userEmail(accessToken.getEmail())
                .userFullName(accessToken.getName()).userUUID(accessToken.getSubject()).build();


    }

    public static String getAccessTokenString(Principal principal){

        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        return keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getTokenString();



    }


}
