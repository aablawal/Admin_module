package com.unionbankng.future.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.enums.UserType;
import com.unionbankng.future.authorizationserver.pojos.RegistrationRequest;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NegativeRegistrationTest extends AbstractTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void emailAlreadyExist() throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("Okeme");
        request.setLastName("Christian");
        request.setPassword("password");
        request.setDialingCode("234");
        request.setUsername("Baba100");
        request.setEmail("abc@gmail.com");
        request.setPhoneNumber("8176267145");
        request.setUserType(UserType.EMPLOYER);

        String body = mapper.writeValueAsString(request);


        Mockito.when(userRepository.existsByEmail("abc@gmail.com")).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/registration/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());

    }

    @Test
    public void weakPassword() throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("aaa");
        request.setLastName("aaa");
        request.setPassword("password");
        request.setUsername("Baba10");
        request.setDialingCode("234");
        request.setEmail("johndoe@gmail.com");
        request.setPhoneNumber("8176267145");
        request.setUserType(UserType.EMPLOYER);

        String body = mapper.writeValueAsString(request);

        Mockito.when(userRepository.existsByEmail("abc@gmail.com")).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/registration/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotAcceptable());
    }

}
