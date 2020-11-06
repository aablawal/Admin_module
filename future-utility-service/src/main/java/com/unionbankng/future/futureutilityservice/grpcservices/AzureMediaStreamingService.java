package com.unionbankng.future.futureutilityservice.grpcservices;

import com.unionbankng.future.futureutilityservice.enums.Status;
import com.unionbankng.future.futureutilityservice.grpcserver.*;
import com.unionbankng.future.futureutilityservice.services.AzureMediaServiceService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.devh.boot.grpc.server.service.GrpcService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.unionbankng.future.futureutilityservice.grpcserver.AzureMediaStreamingServiceGrpc.getUploadAndGetStreamingLocatorMethod;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

@GrpcService
@RequiredArgsConstructor
public class AzureMediaStreamingService extends AzureMediaStreamingServiceGrpc.AzureMediaStreamingServiceImplBase{

    private final AzureMediaServiceService azureMediaServiceService;

    /**
     */
    @Override
    public io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.AzureMediaServiceRequest> uploadAndGetStreamingLocator(
            io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.StreamingLocatorResponse> responseObserver) {


        return new StreamObserver<>() {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String fileName =null ;
            Status status = Status.IN_PROGRESS;

            @Override
            public void onNext(AzureMediaServiceRequest azureMediaServiceRequest) {
                try{
                    outputStream.write(azureMediaServiceRequest.getFileByte().toByteArray());
                    if(fileName == null)
                        fileName = azureMediaServiceRequest.getFileName();
                }catch (IOException e){
                    this.onError(e);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                status = Status.FAILED;
                this.onCompleted();
            }

            @SneakyThrows
            @Override
            public void onCompleted() {
                outputStream.close();
                StreamingLocatorResponse response = azureMediaServiceService.uploadAndGetStreamingLocator(outputStream.toByteArray(),fileName);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }


    /**
     */
    @Override
    public void getStreamingUrlsFromLocator(com.unionbankng.future.futureutilityservice.grpcserver.StreamLinkRequest request,
                                            io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.StreamLinksResponse> responseObserver) {
        StreamLinksResponse response = azureMediaServiceService.getStreamingUrlsFromLocator(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteOutputAsset(com.unionbankng.future.futureutilityservice.grpcserver.DeleteOutputAssetRequest request,
                                  io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.DeleteOutputAssetReponse> responseObserver) {
        DeleteOutputAssetReponse response = azureMediaServiceService.deleteOutputAsset(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }





}
