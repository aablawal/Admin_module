package com.unionbankng.future.futurebankservice.util;

import com.unionbankng.future.futurebankservice.pojos.JwtUserDetail;
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
}
