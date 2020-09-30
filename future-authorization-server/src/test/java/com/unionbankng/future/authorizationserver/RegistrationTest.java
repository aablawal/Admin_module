package com.unionbankng.future.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.enums.ProfileType;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import com.unionbankng.future.authorizationserver.services.MemcachedHelperService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationTest extends AbstractTest {

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    MemcachedHelperService memcachedHelperService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }


    @Test
    public void registrationSuccessful() throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("Okeme");
        request.setLastName("Christian");
        request.setPassword("Pass@word123");
        request.setUsername("baba100");
        request.setDialingCode("234");
        request.setEmail("chokeme@unionbankng.com");
        request.setPhoneNumber("8176267145");
        request.setProfileType(ProfileType.EMPLOYER);

        String body = mapper.writeValueAsString(request);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/registration/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }


}
