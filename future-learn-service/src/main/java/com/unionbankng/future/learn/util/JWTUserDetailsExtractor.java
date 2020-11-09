package com.unionbankng.future.learn.util;

import com.unionbankng.future.learn.pojo.JwtUserDetail;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Map;

public class JWTUserDetailsExtractor {

    public static JwtUserDetail getUserDetailsFromAuthentication(OAuth2Authentication authentication){

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        Map<String,Object> detailsMap = (Map<String, Object>) details.getDecodedDetails();

        return JwtUserDetail.builder().userId(((Integer)detailsMap.get("userId")).longValue()).userEmail(detailsMap.get("userEmail").toString()).userImg(detailsMap.get("userImg").toString())
                .userFullName(detailsMap.get("userFullName").toString()).userUUID(detailsMap.get("userUUID").toString()).build();


    }
}
