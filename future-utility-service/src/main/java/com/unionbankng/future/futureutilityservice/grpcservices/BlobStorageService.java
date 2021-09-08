package com.unionbankng.future.futureutilityservice.grpcservices;

import com.unionbankng.future.futureutilityservice.enums.BlobType;
import com.unionbankng.future.futureutilityservice.interfaceimpl.AzureBlobStorage;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse;
import com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse;


@GrpcService
@RequiredArgsConstructor
public class BlobStorageService extends com.unionbankng.future.futureutilityservice.grpcserver.BlobStorageServiceGrpc.BlobStorageServiceImplBase {

    private final AzureBlobStorage azureBlobStorage;

    @Override
    public void upload(com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest request,
                       io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse> responseObserver) {

        String url = azureBlobStorage.upload(request.getFileByte().toByteArray(),determineType(request.getBlobType()),request.getFileName());

        StorageUploadResponse response = StorageUploadResponse.newBuilder().setUrl(url).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest request,
                       io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse> responseObserver) {

        azureBlobStorage.delete(request.getIdentifier(),determineType(request.getBlobType()));

        StorageDeleteResponse  response = StorageDeleteResponse.newBuilder().setCode(200).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private BlobType determineType(com.unionbankng.future.futureutilityservice.grpcserver.BlobType grpcBlobType){

        if(grpcBlobType.equals(com.unionbankng.future.futureutilityservice.grpcserver.BlobType.IMAGE))
            return BlobType.IMAGE;

        return  BlobType.VIDEO;

    }
}
