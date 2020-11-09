package com.unionbankng.future.learn.util;

import com.unionbankng.future.learn.pojo.JwtUserDetail;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Map;

public class JWTUserDetailsExtractor {

    public static JwtUserDetail getUserDetailsFromAuthentication(OAuth2Authentication authentication){

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        Map<String,String> detailsMap = (Map<String, String>) details.getDecodedDetails();

        return JwtUserDetail.builder().userEmail(detailsMap.get("userEmail")).userImg(detailsMap.get("userImg"))
                .userFullName(detailsMap.get("userFullName")).userUUID(detailsMap.get("userUUID")).build();


    }
}
