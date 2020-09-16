package com.unionbankng.future.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.entities.Photo;
import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.enums.UserType;
import com.unionbankng.future.authorizationserver.pojos.PhotoAndVideoRequest;
import com.unionbankng.future.authorizationserver.repositories.PhotoRepository;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PhotosControllerTest extends AbstractTest {

    private Long testUserId = 1l;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;

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

        User user = User.builder().userType(UserType.EMPLOYER).firstName("abc").lastName("bbc")
                .email("abc@gmail.com").dialingCode("234").phoneNumber("8176267145").isEnabled(Boolean.TRUE)
                .uuid("12323344555").password(passwordEncoder.encode("password")).build();

        userRepository.save(user);

        accessToken = obtainAccessToken("abc@gmail.com","password","web-client","password");
    }

    @After
    public  void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    public void createPhotoWithoutMediaTest() throws Exception {

        PhotoAndVideoRequest photoAndVideoRequest = new PhotoAndVideoRequest();
        photoAndVideoRequest.setComment("Development specialist at UBN");
        photoAndVideoRequest.setTitle("Development Specialist");
        photoAndVideoRequest.setUserId(testUserId);

        String body = mapper.writeValueAsString(photoAndVideoRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/photos/create_new").file(bodyPart)
                .header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void createPhotoWithMediaTest() throws Exception {

        PhotoAndVideoRequest photoAndVideoRequest = new PhotoAndVideoRequest();
        photoAndVideoRequest.setComment("Development specialist at UBN");
        photoAndVideoRequest.setTitle("Development Specialist");
        photoAndVideoRequest.setUserId(testUserId);

        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "Hello world".getBytes());
        String body = mapper.writeValueAsString(photoAndVideoRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());


        Mockito.when(fileStorageService.storeFile(firstFile,testUserId, BlobType.IMAGE)).thenReturn("http://localhost:8080/filename.txt");

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/photos/create_new")
                .file(firstFile).file(bodyPart).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void updatePhotoTest() throws Exception {

        List<Photo> photos = photoRepository.findAll();
        Photo photo = photos.get(0);

        PhotoAndVideoRequest photoAndVideoRequest = new PhotoAndVideoRequest();
        photoAndVideoRequest.setComment("Development specialist at UBN");
        photoAndVideoRequest.setTitle("Development Specialist");
        photoAndVideoRequest.setUserId(testUserId);
        photoAndVideoRequest.setId(photo.getId());

        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "Hello world".getBytes());
        String body = mapper.writeValueAsString(photoAndVideoRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());


        Mockito.when(fileStorageService.storeFile(firstFile,testUserId, BlobType.IMAGE)).thenReturn("http://localhost:8080/filename.txt");

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/photos/update_existing")
                .file(firstFile).file(bodyPart).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void findPhotoByUserIdTest() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("pageNo","0");
        params.add("limit","10");

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/photos/find_by_user_id/"+testUserId)
                .params(params).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));


    }
}
