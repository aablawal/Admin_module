package com.unionbankng.future.futureutilityservice;

import com.google.protobuf.ByteString;
import com.unionbankng.future.futureutilityservice.grpcserver.*;
import com.unionbankng.future.futureutilityservice.services.AzureMediaServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
public class AzureMediaServiceTest {

    @Autowired
    AzureMediaServiceService azureMediaServiceService;


    @Test
    public void uploadAndGetStreamingLocatorTest() throws IOException {
        File file = new File("src/test/resources/index.mp4");
        AzureMediaServiceRequest azureMediaServiceRequest = AzureMediaServiceRequest.newBuilder()
                .setFileName("index.mp4").setFileByte(ByteString.copyFrom(Files.readAllBytes(file.toPath()))).build();

        StreamingLocatorResponse response = azureMediaServiceService.uploadAndGetStreamingLocator(azureMediaServiceRequest);

        String streamingLocatorName = response.getLocatorName();

        assertTrue(response.getSuccess() == true);

        StreamLinkRequest streamLinkRequest = StreamLinkRequest.newBuilder()
                .setLocatorName(streamingLocatorName).build();

        StreamLinksResponse linksResponse = azureMediaServiceService.getStreamingUrlsFromLocator(streamLinkRequest);

        assertTrue(linksResponse.getSuccess() == true);
        System.out.println(linksResponse.getCommaSeperatedStreamingLinks());

        DeleteOutputAssetRequest deleteOutputAssetRequest = DeleteOutputAssetRequest.newBuilder()
                .setAssetName(response.getAssetName()).build();

        DeleteOutputAssetReponse deleteOutputAssetReponse = azureMediaServiceService.deleteOutputAsset(deleteOutputAssetRequest);
        assertTrue(deleteOutputAssetReponse.getSuccess() == true);

    }



}
