package com.unionbankng.future.authorizationserver;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.AuthProvider;
import com.unionbankng.future.authorizationserver.enums.ProfileType;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import org.junit.After;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthorizationServerApplication.class,properties = {
        "grpc.server.port=-1" // Disable external server
})
@WebAppConfiguration
public abstract class AbstractTest {

    protected MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    KeycloakSpringBootProperties props;


    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilterChain).build();

        User user = User.builder().firstName("abc").lastName("bbc")
                .username("test")
                .authProvider(AuthProvider.EMAIL)
                .email("abc@gmail.com").dialingCode("234").phoneNumber("8176267145").isEnabled(Boolean.TRUE)
                .uuid("12323344555").password(passwordEncoder.encode("test123")).build();
        userRepository.save(user);
        
    }

    @After
    public  void tearDown(){
        userRepository.deleteAll();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    public String obtainAccessToken(String username, String password,String client, String clientSecret) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "okemedjbabs@gmail.com");
        params.add("password", "password");
        params.add("grant_type", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(props.getResource(), (String) props.getCredentials().get("secret"));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://testauth.kula.work/auth/realms/Sidekiq/protocol/openid-connect/token";
        ResponseEntity<String> response
                = restTemplate.postForEntity(fooResourceUrl,request, String.class);


        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(response.getBody()).get("access_token").toString();

    }
}