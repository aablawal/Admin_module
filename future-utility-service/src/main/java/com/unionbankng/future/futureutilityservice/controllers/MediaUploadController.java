package com.unionbankng.future.futureutilityservice.controllers;

import com.unionbankng.future.futureutilityservice.enums.BlobType;
import com.unionbankng.future.futureutilityservice.interfaceimpl.AzureBlobStorage;
import com.unionbankng.future.futureutilityservice.pojos.APIResponse;
import com.unionbankng.future.futureutilityservice.pojos.UploadMediaFromURLRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class MediaUploadController {

    private final AzureBlobStorage azureBlobStorage;

    @PostMapping(value = "/v1/media/upload_from_url")
    public ResponseEntity<APIResponse<String>> mediaFromURL(@RequestBody UploadMediaFromURLRequest request) throws IOException {
            String response = azureBlobStorage.uploadFromURL(request.getUrl(), request.getType(), request.getFilename());
            return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,response));
    }

    @PostMapping(value = "/v1/media/upload_from_multipart", consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<String>> imageUploadFromMultipart(@RequestPart("file") MultipartFile file, @RequestPart("type") BlobType type) {

        try {
            String response = azureBlobStorage.upload(file.getBytes(), type,file.getName());
            return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,response));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(new APIResponse<>("Request Failed",false,null));

    }
}
