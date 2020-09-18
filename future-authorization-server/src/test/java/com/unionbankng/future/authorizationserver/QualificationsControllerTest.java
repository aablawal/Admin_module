package com.unionbankng.future.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.entities.PortfolioItem;
import com.unionbankng.future.authorizationserver.entities.Qualification;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.UserType;
import com.unionbankng.future.authorizationserver.pojos.PortfolioItemRequest;
import com.unionbankng.future.authorizationserver.pojos.QualificationRequest;
import com.unionbankng.future.authorizationserver.repositories.QualificationRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.services.FileStorageService;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QualificationsControllerTest extends AbstractTest {

    private Long testUserId = 1l;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QualificationRepository qualificationRepository;

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
    public void createQualificationWithoutMediaTest() throws Exception {

        QualificationRequest qualificationRequest = new QualificationRequest();
        qualificationRequest.setDescription("Development specialist at UBN");
        qualificationRequest.setActivities("Demo activity");
        qualificationRequest.setDegree("Bsc Electronics and Computer Technology");
        qualificationRequest.setEndYear("2017");
        qualificationRequest.setStartYear("2012");
        qualificationRequest.setFieldOfStudy("Electronics And Computer Technology");
        qualificationRequest.setGrade("1st Class");
        qualificationRequest.setSchool("University Of Calabar");
        qualificationRequest.setUserId(testUserId);

        String body = mapper.writeValueAsString(qualificationRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/qualifications/create_new").file(bodyPart)
                .header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void createQualificationWithMediaTest() throws Exception {

        QualificationRequest qualificationRequest = new QualificationRequest();
        qualificationRequest.setDescription("Development specialist at UBN");
        qualificationRequest.setActivities("Demo activity");
        qualificationRequest.setDegree("Bsc Electronics and Computer Technology");
        qualificationRequest.setEndYear("2017");
        qualificationRequest.setStartYear("2012");
        qualificationRequest.setFieldOfStudy("Electronics And Computer Technology");
        qualificationRequest.setGrade("1st Class");
        qualificationRequest.setSchool("University Of Calabar");
        qualificationRequest.setUserId(testUserId);

        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "Hello world".getBytes());
        String body = mapper.writeValueAsString(qualificationRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());


        Mockito.when(fileStorageService.storeFile(firstFile,testUserId, BlobType.VIDEO)).thenReturn("http://localhost:8080/filename.txt");

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/qualifications/create_new")
                .file(firstFile).file(bodyPart).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void updateQualificationTest() throws Exception {

        Qualification qualification = new Qualification();
        qualification.setDescription("Development specialist at UBN");
        qualification.setActivities("Demo activity");
        qualification.setDegree("Bsc Electronics and Computer Technology");
        qualification.setEndYear("2017");
        qualification.setStartYear("2012");
        qualification.setFieldOfStudy("Electronics And Computer Technology");
        qualification.setGrade("1st Class");
        qualification.setSchool("University Of Calabar");
        qualification.setUserId(testUserId);
        qualification = qualificationRepository.save(qualification);

        QualificationRequest qualificationRequest = new QualificationRequest();
        qualificationRequest.setDescription("Development specialist at UBN");
        qualificationRequest.setActivities("Demo activity");
        qualificationRequest.setDegree("Bsc Electronics and Computer Technology");
        qualificationRequest.setEndYear("2017");
        qualificationRequest.setStartYear("2012");
        qualificationRequest.setFieldOfStudy("Electronics And Computer Technology");
        qualificationRequest.setGrade("1st Class");
        qualificationRequest.setSchool("University Of Calabar");
        qualificationRequest.setUserId(testUserId);
        qualificationRequest.setQualificationId(qualification.getId());

        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "Hello world".getBytes());
        String body = mapper.writeValueAsString(qualificationRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());


        Mockito.when(fileStorageService.storeFile(firstFile,testUserId, BlobType.VIDEO)).thenReturn("http://localhost:8080/filename.txt");

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/qualifications/update_existing")
                .file(firstFile).file(bodyPart).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void findPortfolioItemByUserIdTest() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("pageNo","0");
        params.add("limit","10");

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/qualifications/find_by_user_id/"+testUserId)
                .params(params).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));


    }
}
