package com.unionbankng.future.futureutilityservice.grpcservices;

import com.unionbankng.future.futureutilityservice.grpcserver.*;
import com.unionbankng.future.futureutilityservice.services.AzureMediaServiceService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class AzureMediaStreamingService extends AzureMediaStreamingServiceGrpc.AzureMediaStreamingServiceImplBase{

    private final AzureMediaServiceService azureMediaServiceService;


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
