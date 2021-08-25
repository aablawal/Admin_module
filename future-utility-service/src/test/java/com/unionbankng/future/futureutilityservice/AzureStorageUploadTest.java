package com.unionbankng.future.futureutilityservice;

import com.unionbankng.future.futureutilityservice.enums.BlobType;
import com.unionbankng.future.futureutilityservice.interfaceimpl.AzureBlobStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@ActiveProfiles("test")
@SpringBootTest
public class AzureStorageUploadTest {

    @Autowired
    AzureBlobStorage azureBlobStorage;

    @Test
    public void uploadAndDeleteTest() throws IOException {
        File file = new File("test.txt");
        file.createNewFile();
        String url = azureBlobStorage.upload(Files.readAllBytes(file.toPath()), BlobType.IMAGE,"test.txt");
        file.delete();
        System.out.println(url);
        azureBlobStorage.delete("test.txt", BlobType.IMAGE);
    }

    @Test
    public void uploadFromURLAndDeleteTest() throws IOException {
        azureBlobStorage.uploadFromURL("https://kinsta.com/wp-content/uploads/2019/08/jpg-vs-jpeg.jpg", BlobType.IMAGE,"jpg-vs-jpeg.jpg");
        azureBlobStorage.delete("jpg-vs-jpeg.jpg", BlobType.IMAGE);
    }


}
