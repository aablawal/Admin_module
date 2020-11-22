package com.unionbankng.future.learn.services;

import com.unionbankng.future.futureutilityservice.grpcserver.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FutureStreamingService {

    Logger logger = LoggerFactory.getLogger(FutureStreamingService.class);

    @GrpcClient("utilityService")
    private AzureMediaStreamingServiceGrpc.AzureMediaStreamingServiceBlockingStub azureMediaStreamingServiceBlockingStub;


    public StreamLinksResponse getStreamingUrlsFromLocator(String locatorName)  {

        StreamLinkRequest streamLinkRequest = StreamLinkRequest.newBuilder().setLocatorName(locatorName).build();

        return azureMediaStreamingServiceBlockingStub.getStreamingUrlsFromLocator(streamLinkRequest);


    }

    public DeleteOutputAssetReponse deleteOutputAsset(String assetName) {

        DeleteOutputAssetRequest deleteOutputAssetRequest = DeleteOutputAssetRequest.newBuilder().setAssetName(assetName).build();

        return azureMediaStreamingServiceBlockingStub.deleteOutputAsset(deleteOutputAssetRequest);


    }

}
