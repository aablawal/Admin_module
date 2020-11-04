package com.unionbankng.future.learn.services;

import com.google.protobuf.ByteString;
import com.unionbankng.future.futureutilityservice.grpcserver.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FutureStreamingService {

    @GrpcClient("utilityService")
    private AzureMediaStreamingServiceGrpc.AzureMediaStreamingServiceBlockingStub azureMediaStreamingServiceBlockingStub;


    public StreamingLocatorResponse uploadAndGetStreamingLocator(MultipartFile file) throws IOException {

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString() + fileExtension;

        AzureMediaServiceRequest azureMediaServiceRequest = AzureMediaServiceRequest.newBuilder()
                .setFileByte(ByteString.copyFrom(file.getBytes())).setFileName(fileName).build();

        return azureMediaStreamingServiceBlockingStub.uploadAndGetStreamingLocator(azureMediaServiceRequest);


    }

    public StreamLinksResponse getStreamingUrlsFromLocator(String locatorName)  {

        StreamLinkRequest streamLinkRequest = StreamLinkRequest.newBuilder().setLocatorName(locatorName).build();

        return azureMediaStreamingServiceBlockingStub.getStreamingUrlsFromLocator(streamLinkRequest);


    }

    public DeleteOutputAssetReponse deleteOutputAsset(String assetName) {

        DeleteOutputAssetRequest deleteOutputAssetRequest = DeleteOutputAssetRequest.newBuilder().setAssetName(assetName).build();

        return azureMediaStreamingServiceBlockingStub.deleteOutputAsset(deleteOutputAssetRequest);


    }

}
