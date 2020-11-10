package com.unionbankng.future.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.entities.UserInterest;
import com.unionbankng.future.authorizationserver.enums.TagType;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.EntitySkillRequest;
import com.unionbankng.future.authorizationserver.pojos.PhotoAndVideoRequest;
import com.unionbankng.future.authorizationserver.repositories.UserInterestRepository;
import com.unionbankng.future.authorizationserver.services.TagService;
import com.unionbankng.future.authorizationserver.services.UserInterestService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserInterestTest extends AbstractTest {

    private Long testUserId = 1l;

    String accessToken;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserInterestRepository userInterestRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void setUp() {
    }

    @Before
    public void createUser() throws Exception {

        super.setUp();

        accessToken = obtainAccessToken("abc@gmail.com","password","web-client","password");
    }

    @Test
    public void addUserInterestsTest() throws Exception {

        List<Tag> tagList = tagService.getAllTagsByType(TagType.INTEREST);
        List<Long> ids = tagList.stream().map(m->m.getId()).collect(Collectors.toList());

        EntitySkillRequest entitySkillRequest = new EntitySkillRequest();
        entitySkillRequest.setEntityId(testUserId);
        entitySkillRequest.setSkillIds(ids);

        String body = mapper.writeValueAsString(entitySkillRequest);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/user_interest/add_for_user").content(body)
                .header("Authorization", "Bearer " + accessToken).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void getUserInterestsTest() throws Exception {


        mvc.perform(MockMvcRequestBuilders.get("/api/v1/user_interest/find_by_user_id/"+testUserId)
                .header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void deleteUserInterestsTest() throws Exception {

        List<Tag> tagList = tagService.getAllTagsByType(TagType.INTEREST);

        UserInterest userInterest = new UserInterest();
        userInterest.setUserId(testUserId);
        userInterest.setSkill(tagList.get(0));
        userInterestRepository.save(userInterest);

        List<UserInterest> userInterests = userInterestRepository.findAllByUserId(testUserId);

        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/user_interest/delete/"+userInterests.get(0).getId())
                .header("Authorization", "Bearer " + accessToken).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }



}
