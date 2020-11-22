package com.unionbankng.future.learn.services;

import com.google.protobuf.ByteString;
import com.unionbankng.future.futureutilityservice.grpcserver.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @GrpcClient("utilityService")
    private  BlobStorageServiceGrpc.BlobStorageServiceBlockingStub blobStorageServiceBlockingStub;


    public String storeFile(MultipartFile file, Long userId,BlobType blobType) throws IOException {

        logger.info("Name is :{}",file.getName());
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String fileName = userId + "_" + UUID.randomUUID().toString() + fileExtension;

        StorageUploadRequest storageUploadRequest = StorageUploadRequest.newBuilder().setFileName(fileName)
                .setBlobType(blobType).setFileByte(ByteString.copyFrom(file.getBytes())).build();

        StorageUploadResponse response = blobStorageServiceBlockingStub.upload(storageUploadRequest);

        return response.getUrl();

    }

    public int deleteFileFromStorage(String source,BlobType blobType) {
        String originalFileName = source.substring(source.lastIndexOf("/") + 1);
        logger.info("Delete from Blob storage where blob client : {}",originalFileName);
        StorageDeleteRequest storageDeleteRequest = StorageDeleteRequest.newBuilder().setIdentifier(originalFileName)
                .setBlobType(blobType).build();

        StorageDeleteResponse response = blobStorageServiceBlockingStub.delete(storageDeleteRequest);

        return response.getCode();

    }
}
