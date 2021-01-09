//package com.unionbankng.future.authorizationserver.security;
//
//import com.unionbankng.future.authorizationserver.entities.User;
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class CustomTokenEnhancer implements TokenEnhancer {
//
//    @Override
//    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//
//        User user = (FutureDAOUserDetails) authentication.getPrincipal();
//        final Map<String, Object> additionalInfo = new HashMap<>();
//
//        additionalInfo.put("userId", user.getId());
//        additionalInfo.put("coverImg",user.getCoverImg());
//        additionalInfo.put("userUUID", user.getUuid());
//        additionalInfo.put("userEmail", user.getEmail());
//        additionalInfo.put("userImg", user.getImg());
//        additionalInfo.put("userFullName", user.toString());
//
//        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
//
//        return accessToken;
//    }
//
//
//}