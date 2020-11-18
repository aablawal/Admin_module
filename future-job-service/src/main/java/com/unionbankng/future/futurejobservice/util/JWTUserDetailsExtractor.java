package com.unionbankng.future.futurejobservice.util;
import com.unionbankng.future.futurejobservice.pojos.JwtUserDetail;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import java.util.Map;
public class JWTUserDetailsExtractor {

    public static JwtUserDetail getUserDetailsFromAuthentication(OAuth2Authentication authentication){

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        Map<String,Object> detailsMap = (Map<String, Object>) details.getDecodedDetails();
        String userImg = detailsMap.get("userImg") == null ? null : detailsMap.get("userImg").toString();

        return JwtUserDetail.builder().userId(((Integer)detailsMap.get("userId")).longValue()).userEmail(detailsMap.get("userEmail").toString()).userImg(userImg)
                .userFullName(detailsMap.get("userFullName").toString()).userUUID(detailsMap.get("userUUID").toString()).build();


    }
}
