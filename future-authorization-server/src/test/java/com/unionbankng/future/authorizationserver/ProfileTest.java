package com.unionbankng.future.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.entities.Profile;
import com.unionbankng.future.authorizationserver.enums.ProfileType;
import com.unionbankng.future.authorizationserver.pojos.ProfileUpdateRequest;
import com.unionbankng.future.authorizationserver.repositories.PortfolioItemRepository;
import com.unionbankng.future.authorizationserver.repositories.ProfileRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.services.FileStorageService;
import com.unionbankng.future.authorizationserver.services.ProfileService;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileTest extends AbstractTest {

    private Long testUserId = 1l;


    @Autowired
    ProfileService profileService;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    FileStorageService fileStorageService;

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
    public void createProfileTest(){

        Profile profile = Profile.builder().userId(testUserId).profileType(ProfileType.BASIC).bio("Test bio").coverPhoto("http://test")
                .isEmployer(false).isFreelancer(true).jobTitle("Test Job title").pricePerHour(BigDecimal.TEN).build();
        profileService.save(profile);
    }

    @After
    public void clearProfiles(){
        profileRepository.deleteAll();
    }

    @Test
    public void updateProfileCoverPhoto() throws Exception {

        Profile profile = Profile.builder().userId(testUserId).profileType(ProfileType.BASIC).bio("Test bio").coverPhoto("http://test")
                .isEmployer(false).isFreelancer(true).jobTitle("Test Job title").pricePerHour(BigDecimal.TEN).build();
        profile = profileService.save(profile);

        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "Hello world".getBytes());

        Mockito.when(fileStorageService.storeFile(firstFile, profile.getId(), BlobType.VIDEO)).thenReturn("http://localhost:8080/filename.txt");

        mvc.perform(MockMvcRequestBuilders.multipart(String.format("/api/v1/profile/%s/update_cover_photo",profile.getId()))
                .file(firstFile).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getProfileById() throws Exception {

        Profile profile = Profile.builder().userId(testUserId).profileType(ProfileType.BASIC).bio("Test bio").coverPhoto("http://test")
                .isEmployer(false).isFreelancer(true).jobTitle("Test Job title").pricePerHour(BigDecimal.TEN).build();
        profileService.save(profile);

        mvc.perform(MockMvcRequestBuilders.get(String.format("/api/v1/profile/get_profile_by_user_id/%s",testUserId))
                .header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProfile() throws Exception {

        Profile profile = Profile.builder().userId(testUserId).profileType(ProfileType.BASIC).bio("Test bio").coverPhoto("http://test")
                .isEmployer(false).isFreelancer(true).jobTitle("Test Job title").pricePerHour(BigDecimal.TEN).build();
        profile = profileService.save(profile);

        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
        profileUpdateRequest.setBio("Updated Test bio");
        profileUpdateRequest.setIsEmployer(true);
        profileUpdateRequest.setIsFreelancer(true);
        profileUpdateRequest.setJobTitle("new Test Job Title");
        profileUpdateRequest.setPricePerHour(BigDecimal.TEN);
        profileUpdateRequest.setProfileType(ProfileType.EMPLOYER);
        String body = mapper.writeValueAsString(profileUpdateRequest);

        mvc.perform(MockMvcRequestBuilders.post(String.format("/api/v1/profile/update_profile/%s",profile.getId()))
                .content(body).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
