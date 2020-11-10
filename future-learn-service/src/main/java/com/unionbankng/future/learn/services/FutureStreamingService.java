package com.unionbankng.future.learn.services;

import com.google.protobuf.ByteString;
import com.unionbankng.future.futureutilityservice.grpcserver.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class FutureStreamingService {

    Logger logger = LoggerFactory.getLogger(FutureStreamingService.class);

    @GrpcClient("utilityService")
    private AzureMediaStreamingServiceGrpc.AzureMediaStreamingServiceBlockingStub azureMediaStreamingServiceBlockingStub;
    @GrpcClient("utilityService")
    private AzureMediaStreamingServiceGrpc.AzureMediaStreamingServiceStub azureMediaStreamingServiceStub;

    public StreamingLocatorResponse uploadAndGetStreamingLocator(MultipartFile file) throws IOException, InterruptedException {


        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString() + fileExtension;

        final StreamingLocatorResponse[] streamingResponse = {null};
        // request observer
        final CountDownLatch finishLatch = new CountDownLatch(1);

        io.grpc.stub.StreamObserver<AzureMediaServiceRequest> streamObserver = azureMediaStreamingServiceStub.
                withDeadlineAfter(120000, TimeUnit.MILLISECONDS).uploadAndGetStreamingLocator(new StreamObserver<>() {

            @Override
            public void onNext(StreamingLocatorResponse streamingLocatorResponse) {
                logger.info("response is {}",streamingLocatorResponse);
                if(streamingLocatorResponse != null) {
                    streamingResponse[0] = streamingLocatorResponse;
                    finishLatch.countDown();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                logger.info("Oops an error occured while uploading your file");
                streamingResponse[0] = StreamingLocatorResponse.newBuilder().setSuccess(false).build();
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                logger.info("upload completed");
                finishLatch.countDown();
            }
        });

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


        if(!finishLatch.await(2,TimeUnit.MINUTES)){
            logger.info("didn't finnish");
             return streamingResponse[0];
        }

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
