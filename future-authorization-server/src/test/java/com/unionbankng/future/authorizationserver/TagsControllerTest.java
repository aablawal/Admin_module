package com.unionbankng.future.authorizationserver;

import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TagsControllerTest extends AbstractTest {

    @Autowired
    UserRepository userRepository;

    String accessToken;

    @Override
    public void setUp() {
    }

    @Before
    public void createUser() throws Exception {

        super.setUp();

        accessToken = obtainAccessToken("abc@gmail.com","password","web-client","password");
    }

    @Test
    public void getByTypeAndNameLikeTest() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("type","INTEREST");
        params.add("name","Desi");
        params.add("pageNo","0");
        params.add("limit","10");

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/tags/find_by_type_likely_name")
                .params(params).header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk());


    }

    @Test
    public void getByType() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("type","INTEREST");
        params.add("pageNo","0");
        params.add("limit","10");

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/tags/find_by_type")
                .params(params).header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));;


    }


}
