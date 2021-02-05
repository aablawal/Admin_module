package com.unionbankng.future.futureutilityservice.interfaceimpl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobErrorCode;
import com.azure.storage.blob.models.BlobStorageException;
import com.unionbankng.future.futureutilityservice.enums.BlobType;
import com.unionbankng.future.futureutilityservice.interfaces.BlobStorage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class AzureBlobStorage implements BlobStorage {

    private final BlobContainerClient videoContainerClient;
    private final BlobContainerClient imageContainerClient;

    private Logger logger = LoggerFactory.getLogger(AzureBlobStorage.class);

    @Override
    public String upload(byte[] fileByte, BlobType blobType, String fileName) {

        InputStream targetStream = new ByteArrayInputStream(fileByte);
        BlobClient blobClient = blobType.equals(BlobType.IMAGE) ? imageContainerClient.getBlobClient(fileName)
                : videoContainerClient.getBlobClient(fileName);

        logger.info("Uploading to Blob storage as blob: {}",blobClient.getBlobUrl());
        blobClient.upload(targetStream,fileByte.length );

        return blobClient.getBlobUrl();

    }

    @Override
    public void delete(String identifier,BlobType blobType){

        BlobClient blobClient = blobType.equals(BlobType.IMAGE) ? imageContainerClient.getBlobClient(identifier)
                : videoContainerClient.getBlobClient(identifier);

        try{
            blobClient.delete();
        }catch (BlobStorageException ex) {

            // The container may already exist, so don't throw an error
            if (!ex.getErrorCode().equals(BlobErrorCode.BLOB_NOT_FOUND)) {
                throw ex;
            }
        }


    }
}
