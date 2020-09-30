package com.unionbankng.future.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.entities.Video;
import com.unionbankng.future.authorizationserver.pojos.PhotoAndVideoRequest;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.repositories.VideoRepository;
import com.unionbankng.future.authorizationserver.services.FileStorageService;
import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
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

public class VideosControllerTest extends AbstractTest {

    private Long testUserId = 1l;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VideoRepository videoRepository;

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
    public void createVideoWithoutMediaTest() throws Exception {

        PhotoAndVideoRequest photoAndVideoRequest = new PhotoAndVideoRequest();
        photoAndVideoRequest.setComment("Development specialist at UBN");
        photoAndVideoRequest.setTitle("Development Specialist");
        photoAndVideoRequest.setProfileId(testUserId);

        String body = mapper.writeValueAsString(photoAndVideoRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/videos/create_new").file(bodyPart)
                .header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void createVideoWithMediaTest() throws Exception {

        PhotoAndVideoRequest photoAndVideoRequest = new PhotoAndVideoRequest();
        photoAndVideoRequest.setComment("Development specialist at UBN");
        photoAndVideoRequest.setTitle("Development Specialist");
        photoAndVideoRequest.setProfileId(testUserId);

        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "Hello world".getBytes());
        String body = mapper.writeValueAsString(photoAndVideoRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());


        Mockito.when(fileStorageService.storeFile(firstFile,testUserId, BlobType.VIDEO)).thenReturn("http://localhost:8080/filename.txt");

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/videos/create_new")
                .file(firstFile).file(bodyPart).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void updateVideoTest() throws Exception {

        Video video = new Video();
        video.setSource("http://localhost:8080/filename.txt");
        video.setComment("test");
        video.setTitle("title");
        video.setUserId(testUserId);
        video = videoRepository.save(video);

        PhotoAndVideoRequest photoAndVideoRequest = new PhotoAndVideoRequest();
        photoAndVideoRequest.setComment("Development specialist at UBN");
        photoAndVideoRequest.setTitle("Development Specialist");
        photoAndVideoRequest.setProfileId(testUserId);
        photoAndVideoRequest.setId(video.getId());

        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "Hello world".getBytes());
        String body = mapper.writeValueAsString(photoAndVideoRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());


        Mockito.when(fileStorageService.storeFile(firstFile,testUserId, BlobType.VIDEO)).thenReturn("http://localhost:8080/filename.txt");

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/videos/update_existing")
                .file(firstFile).file(bodyPart).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void findVideoByUserIdTest() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("pageNo","0");
        params.add("limit","10");

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/videos/find_by_user_id/"+testUserId)
                .params(params).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));


    }
}
