package com.unionbankng.future.authorizationserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.authorizationserver.entities.PortfolioItem;
import com.unionbankng.future.authorizationserver.pojos.PortfolioItemRequest;
import com.unionbankng.future.authorizationserver.repositories.PortfolioItemRepository;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
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

public class PortfolioItemsControllerTest extends AbstractTest {

    private Long testProfileId = 1l;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PortfolioItemRepository portfolioItemRepository;

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
    public void createPortfolioItemWithoutMediaTest() throws Exception {

        PortfolioItemRequest photoAndVideoRequest = new PortfolioItemRequest();
        photoAndVideoRequest.setDescription("Development specialist at UBN");
        photoAndVideoRequest.setLink("http://localhost:8080/test");
        photoAndVideoRequest.setTitle("Development Specialist");
        photoAndVideoRequest.setProfileId(testProfileId);

        String body = mapper.writeValueAsString(photoAndVideoRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/portfolio_items/create_new").file(bodyPart)
                .header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void createPortfolioItemWithMediaTest() throws Exception {

        PortfolioItemRequest photoAndVideoRequest = new PortfolioItemRequest();
        photoAndVideoRequest.setDescription("Development specialist at UBN");
        photoAndVideoRequest.setLink("http://localhost:8080/test");
        photoAndVideoRequest.setTitle("Development Specialist");
        photoAndVideoRequest.setProfileId(testProfileId);

        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "Hello world".getBytes());
        String body = mapper.writeValueAsString(photoAndVideoRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());


        Mockito.when(fileStorageService.storeFile(firstFile, testProfileId, BlobType.VIDEO)).thenReturn("http://localhost:8080/filename.txt");

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/portfolio_items/create_new")
                .file(firstFile).file(bodyPart).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void updatePortfolioItemTest() throws Exception {

        PortfolioItem portfolioItem = new PortfolioItem();
        portfolioItem.setMedia("http://localhost:8080/filename.txt");
        portfolioItem.setDescription("test");
        portfolioItem.setLink("http://localhost:8080");
        portfolioItem.setTitle("title");
        portfolioItem.setProfileId(testProfileId);
        portfolioItem = portfolioItemRepository.save(portfolioItem);

        PortfolioItemRequest portfolioItemRequest = new PortfolioItemRequest();
        portfolioItemRequest.setDescription("Development specialist at UBN");
        portfolioItemRequest.setTitle("Development Specialist");
        portfolioItemRequest.setProfileId(testProfileId);
        portfolioItemRequest.setLink("http://localhost:8080");
        portfolioItemRequest.setPortfolioItemId(portfolioItem.getId());

        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "Hello world".getBytes());
        String body = mapper.writeValueAsString(portfolioItemRequest);
        MockMultipartFile bodyPart = new MockMultipartFile("request", "", "application/json", body.getBytes());


        Mockito.when(fileStorageService.storeFile(firstFile, testProfileId, BlobType.VIDEO)).thenReturn("http://localhost:8080/filename.txt");

        mvc.perform(MockMvcRequestBuilders.multipart("/api/v1/portfolio_items/update_existing")
                .file(firstFile).file(bodyPart).header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    public void findPortfolioItemByUserIdTest() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("pageNo","0");
        params.add("limit","10");

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/portfolio_items/find_by_profile_id/"+ testProfileId)
                .params(params).header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));


    }
}
