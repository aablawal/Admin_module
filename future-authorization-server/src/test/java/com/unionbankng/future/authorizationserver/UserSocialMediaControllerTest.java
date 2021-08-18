//package com.unionbankng.future.authorizationserver;
//
//import com.unionbankng.future.authorizationserver.enums.SocialMedia;
//import com.unionbankng.future.authorizationserver.pojos.LongLivedInstagramResponse;
//import com.unionbankng.future.authorizationserver.pojos.ShortlivedInstagramResponse;
//import com.unionbankng.future.authorizationserver.repositories.UserRepository;
//import com.unionbankng.future.authorizationserver.utils.InstagramHandler;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class UserSocialMediaControllerTest extends AbstractTest {
//
//    private Long userId = 1l;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @MockBean
//    InstagramHandler instagramHandler;
//
//    String accessToken;
//
//    @Override
//    public void setUp() {
//    }
//
//    @Before
//    public void createUser() throws Exception {
//
//        super.setUp();
//
//        accessToken = obtainAccessToken("abc@gmail.com","password","web-client","password");
//    }
//
//
//    @Test
//    public void linkSocialMediaWrongSocialCodeTest() throws Exception {
//
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("userId", userId.toString());
//        params.add("socialMedia", SocialMedia.INSTAGRAM.name());
//        params.add("socialCode","1/fFAGRNJru1FTz70BzhT3Zg");
//
//        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/user_social_media/link_social").params(params)
//                .header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isConflict());
//
//
//    }
//
//    @Test
//    public void linkSocialMediaSuccessTest() throws Exception {
//
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("userId", userId.toString());
//        params.add("socialMedia", SocialMedia.INSTAGRAM.name());
//        params.add("socialCode","1/fFAGRNJru1FTz70BzhT3Zg");
//
//        ShortlivedInstagramResponse shortlivedInstagramResponse = new ShortlivedInstagramResponse();
//        shortlivedInstagramResponse.setAccess_token("tylopeerrrmsjdjdjdhhfhffhfhhfjjf");
//        shortlivedInstagramResponse.setUser_id(1l);
//        Mockito.when(instagramHandler.getShortLivedToken(any(String.class))).thenReturn(shortlivedInstagramResponse);
//
//        LongLivedInstagramResponse longLivedInstagramResponse = new LongLivedInstagramResponse();
//        longLivedInstagramResponse.setAccess_token("tylopeerrrmsjdjdjdhhfhffhfhhfjjf");
//        longLivedInstagramResponse.setExpires_in(1000l);
//        longLivedInstagramResponse.setToken_type("Authorization");
//        Mockito.when(instagramHandler.getLongLivedToken(any(String.class))).thenReturn(longLivedInstagramResponse);
//
//        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/user_social_media/link_social").params(params)
//                .header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//
//    }
//
//
//}
