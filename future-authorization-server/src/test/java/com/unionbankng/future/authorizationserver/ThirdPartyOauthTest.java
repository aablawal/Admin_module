package com.unionbankng.future.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.entities.Photo;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.AuthProvider;
import com.unionbankng.future.authorizationserver.interfaceimpl.GoogleOauthProvider;
import com.unionbankng.future.authorizationserver.pojos.PhotoAndVideoRequest;
import com.unionbankng.future.authorizationserver.pojos.ThirdPartyOauthResponse;
import com.unionbankng.future.authorizationserver.repositories.PhotoRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.services.FileStorageService;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThirdPartyOauthTest extends AbstractTest {


    @MockBean
    private GoogleOauthProvider googleOauthProvider;

    @Override
    public void setUp() {
    }

    @Before
    public void createUser() throws Exception {

        super.setUp();
    }

    @Test
    public void noThirdPartyOauthTokenProvidedTest() throws Exception {

        User user = User.builder().firstName("abc").lastName("bbc")
                .username("testuser")
                .authProvider(AuthProvider.GOOGLE)
                .email("okemedjbabs@gmail.com").dialingCode("234").phoneNumber("8076267145").isEnabled(Boolean.TRUE)
                .uuid("123233466555").build();

        userRepository.save(user);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "okemedjbabs@gmail.com");
        params.add("grant_type", "password");

        ResultActions result = mvc
                .perform(MockMvcRequestBuilders.post("/oauth/token").params(params)
                        .with(httpBasic("web-client","password")).accept("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest()).andExpect(content().contentType("application/json;charset=UTF-8"));


    }

    @Test
    public void successThirdPartyOauthTokenProvidedTest() throws Exception {

        User user = User.builder().firstName("abc").lastName("bbc")
                .username("testuser")
                .authProvider(AuthProvider.GOOGLE)
                .email("okemedjbabs@gmail.com").dialingCode("234").phoneNumber("8076267145").isEnabled(Boolean.TRUE)
                .uuid("123233466555").build();

        userRepository.save(user);

        ThirdPartyOauthResponse thirdPartyOauthResponse = new ThirdPartyOauthResponse();

        Mockito.when(googleOauthProvider.authentcate("1/fFAGRNJru1FTz70BzhT3Zg")).thenReturn(thirdPartyOauthResponse);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "okemedjbabs@gmail.com");
        params.add("grant_type", "password");
        params.add("oauth_token","1/fFAGRNJru1FTz70BzhT3Zg");

       mvc
                .perform(MockMvcRequestBuilders.post("/oauth/token").params(params)
                        .with(httpBasic("web-client","password")).accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));


    }



}
