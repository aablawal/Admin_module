package com.unionbankng.future.futureutilityservice.controllers;

import com.unionbankng.future.futureutilityservice.enums.BlobType;
import com.unionbankng.future.futureutilityservice.interfaceimpl.AzureBlobStorage;
import com.unionbankng.future.futureutilityservice.pojos.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class VideoUploadController {

    private final AzureBlobStorage azureBlobStorage;

    @PostMapping(value = "/v1/video/upload", consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<String>> addNewExperience(@RequestPart("file") MultipartFile file) {

        try {
            String response = azureBlobStorage.upload(file.getBytes(), BlobType.VIDEO,file.getName());
            return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,response));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(new APIResponse<>("Request Failed",false,null));

    }
}
