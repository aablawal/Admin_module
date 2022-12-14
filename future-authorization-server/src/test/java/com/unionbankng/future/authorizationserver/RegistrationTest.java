package com.unionbankng.future.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.enums.AuthProvider;
import com.unionbankng.future.authorizationserver.enums.ProfileType;
import com.unionbankng.future.authorizationserver.interfaceimpl.GoogleOauthProvider;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import com.unionbankng.future.authorizationserver.pojos.ThirdPartyOauthResponse;
import com.unionbankng.future.authorizationserver.services.MemcachedHelperService;
import com.unionbankng.future.authorizationserver.services.RegistrationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.net.URI;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationTest extends AbstractTest {

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GoogleOauthProvider googleOauthProvider;

    @MockBean
    private RegistrationService registrationService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }


    @Test
    public void emailRegistrationSuccessful() throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("Okeme");
        request.setLastName("Christian");
        request.setPassword("Pass@word123");
        request.setUsername("baba100");
        request.setDialingCode("234");
        request.setEmail("chokeme@unionbankng.com");
        request.setPhoneNumber("8176267145");
        request.setAuthProvider(AuthProvider.EMAIL);

        String body = mapper.writeValueAsString(request);

//        Response response = Response.created(URI.create("https://test.com/"+ UUID.randomUUID().toString())).build();
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/registration/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());


    }

    @Test
    public void googleRegistrationSuccessful() throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("baba100");
        request.setPassword("Pass@word123");
        request.setThirdPartyToken("1/fFAGRNJru1FTz70BzhT3Zg");
        request.setAuthProvider(AuthProvider.GOOGLE);

        String body = mapper.writeValueAsString(request);

        ThirdPartyOauthResponse thirdPartyOauthResponse = new ThirdPartyOauthResponse();
        thirdPartyOauthResponse.setLastName("Okeme");
        thirdPartyOauthResponse.setFirstName("Christian");
        thirdPartyOauthResponse.setEmail("chokeme@unionbankng.com");
        thirdPartyOauthResponse.setImage("https://localhost:8080/test_image.com");

        Mockito.when(googleOauthProvider.authentcate("1/fFAGRNJru1FTz70BzhT3Zg")).thenReturn(thirdPartyOauthResponse);
//        Response response = Response.created(URI.create("https://test.com/"+ UUID.randomUUID().toString())).build();

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/registration/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }




}
