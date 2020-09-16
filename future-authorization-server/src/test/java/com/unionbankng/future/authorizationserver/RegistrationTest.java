package com.unionbankng.future.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.enums.UserType;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import com.unionbankng.future.authorizationserver.repositories.UserConfirmationTokenRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationTest extends AbstractTest {

    @Autowired
    private ObjectMapper mapper;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConfirmationTokenRepository userConfirmationTokenRepository;

    @Test
    public void registrationSuccessful() throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("Okeme");
        request.setLastName("Christian");
        request.setPassword("Pass@word123");
        request.setUsername("djbabs");
        request.setDialingCode("234");
        request.setEmail("chokeme@unionbankng.com");
        request.setPhoneNumber("8176267145");
        request.setCountry("NGN");
        request.setStateOfResidence("Lagos");
        request.setAddress("My test house");
        request.setUserType(UserType.EMPLOYER);

        String body = mapper.writeValueAsString(request);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/registration/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


    }

    @After
    public  void tearDown(){
        userConfirmationTokenRepository.deleteAll();
        userRepository.deleteAll();
    }
}
