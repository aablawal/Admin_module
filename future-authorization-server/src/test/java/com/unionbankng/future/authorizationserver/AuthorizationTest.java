package com.unionbankng.future.authorizationserver;

import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorizationTest extends AbstractTest{

    @Autowired
    UserRepository userRepository;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void invalidCredentials() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "mkeeeetest");
        params.add("password", "fakePassword@11111");
        params.add("grant_type", "password");

        mvc
                .perform(MockMvcRequestBuilders.post("/oauth/token").params(params)
                        .with(httpBasic("web-client", "password")).accept("application/json;charset=UTF-8"))
                .andExpect(status().isUnauthorized()).andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void badClientCredential() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "mkeeeetest");
        params.add("password", "fakePassword@11111");
        params.add("grant_type", "password");

        mvc
                .perform(MockMvcRequestBuilders.post("/oauth/token").params(params)
                        .with(httpBasic("web-client", "passTword")).accept("application/json;charset=UTF-8"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void authenticationSuccessful() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "abc@gmail.com");
        params.add("password", "password");
        params.add("grant_type", "password");

        ResultActions result = mvc
                .perform(MockMvcRequestBuilders.post("/oauth/token").params(params)
                        .with(httpBasic("web-client", "password")).accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));

    }


}
