package com.unionbankng.future.futureutilityservice.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobErrorCode;
import com.azure.storage.blob.models.BlobStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AzureBlobStorageConfiguration {

    @Value("${azure.storage.connectionstring}")
    private String azureBlobStorageConnectionString;

    BlobServiceClient blobServiceClient(){
        // Create a BlobServiceClient object which will be used to create a container client
        return new BlobServiceClientBuilder().connectionString(azureBlobStorageConnectionString).buildClient();

    }

    @Bean
    BlobContainerClient videoContainerClient(){
        String containerName = "videos";

        try{
            return blobServiceClient().createBlobContainer(containerName);
        } catch (BlobStorageException ex) {

            // The container may already exist, so don't throw an error
            if (!ex.getErrorCode().equals(BlobErrorCode.CONTAINER_ALREADY_EXISTS)) {
                throw ex;
            }
        }
        return blobServiceClient().getBlobContainerClient(containerName);
    }

    @Bean
    @Primary
    BlobContainerClient imageContainerClient(){
        String containerName = "images";
        // Create the container and return a container client object
        try{
            return blobServiceClient().createBlobContainer(containerName);
        } catch (BlobStorageException ex) {

            // The container may already exist, so don't throw an error
            if (!ex.getErrorCode().equals(BlobErrorCode.CONTAINER_ALREADY_EXISTS)) {
                throw ex;
            }
        }
        return blobServiceClient().getBlobContainerClient(containerName);
    }
}
