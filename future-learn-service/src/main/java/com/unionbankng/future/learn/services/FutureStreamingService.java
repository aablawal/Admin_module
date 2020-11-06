package com.unionbankng.future.learn.services;

import com.google.protobuf.ByteString;
import com.unionbankng.future.futureutilityservice.grpcserver.*;
import com.unionbankng.future.learn.pojo.StreamingLocatorObserver;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FutureStreamingService {

    @GrpcClient("utilityService")
    private AzureMediaStreamingServiceGrpc.AzureMediaStreamingServiceBlockingStub azureMediaStreamingServiceBlockingStub;
    @GrpcClient("utilityService")
    private AzureMediaStreamingServiceGrpc.AzureMediaStreamingServiceStub azureMediaStreamingServiceStub;

    public StreamingLocatorResponse uploadAndGetStreamingLocator(MultipartFile file) throws IOException {

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString() + fileExtension;

        final StreamingLocatorResponse[] streamingResponse = {null};
        // request observer
        io.grpc.stub.StreamObserver<AzureMediaServiceRequest> streamObserver = this.azureMediaStreamingServiceStub.uploadAndGetStreamingLocator(new StreamingLocatorObserver());


        InputStream inputStream = file.getInputStream();
        byte[] bytes = new byte[4096];
        int size;
        while ((size = inputStream.read(bytes)) > 0){
            AzureMediaServiceRequest azureMediaServiceRequest =AzureMediaServiceRequest.newBuilder()
                    .setFileByte(ByteString.copyFrom(bytes, 0 , size)).setFileName(fileName).build();
            streamObserver.onNext(azureMediaServiceRequest);
        }

        inputStream.close();
        streamObserver.onCompleted();
        return streamingResponse[0];



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
