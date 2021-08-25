package com.unionbankng.future.futureutilityservice;

import com.google.protobuf.ByteString;
import com.unionbankng.future.futureutilityservice.grpcserver.*;
import com.unionbankng.future.futureutilityservice.grpcservices.BlobStorageService;
import io.grpc.internal.testing.StreamRecorder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.*;

@ActiveProfiles("test")
@SpringBootTest
public class GrpcBlobStorageServiceTest {

    @Autowired
    BlobStorageService blobStorageService;
    String testFileName = "test.txt";

    @Test
    public void storageUploadTest() throws Exception {
        File file = new File(testFileName);
        file.createNewFile();
        StorageUploadRequest storageUploadRequest = StorageUploadRequest.newBuilder().setBlobType(BlobType.IMAGE)
                .setFileByte(ByteString.copyFrom(Files.readAllBytes(file.toPath()))).setFileName(testFileName).build();
        StreamRecorder<StorageUploadResponse> responseObserver = StreamRecorder.create();
        blobStorageService.upload(storageUploadRequest,responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        List<StorageUploadResponse> results = responseObserver.getValues();

        assertEquals(1, results.size());

        StorageUploadResponse response = results.get(0);
        assertNotNull(response.getUrl());

        file.delete();
    }

    @Test
    public void storageDeleteTest() throws Exception {

        StorageDeleteRequest storageDeleteRequest = StorageDeleteRequest.newBuilder().setBlobType(BlobType.IMAGE)
                .setIdentifier(testFileName).build();
        StreamRecorder<StorageDeleteResponse> responseObserver = StreamRecorder.create();
        blobStorageService.delete(storageDeleteRequest,responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        List<StorageDeleteResponse> results = responseObserver.getValues();

        assertEquals(1, results.size());

        StorageDeleteResponse response = results.get(0);
        assertEquals(response.getCode(),200);
    }
}
