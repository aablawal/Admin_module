package com.unionbankng.future.futureutilityservice.controllers;

import com.unionbankng.future.futureutilityservice.pojos.APIResponse;
import com.unionbankng.future.futureutilityservice.pojos.StreamingLocatorResponse;
import com.unionbankng.future.futureutilityservice.services.AzureMediaServiceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class VideoStreamUploadController {

    Logger logger = LoggerFactory.getLogger(VideoStreamUploadController.class);
    private final AzureMediaServiceService azureMediaServiceService;

    @PostMapping(value = "/v1/video_stream/upload", consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<StreamingLocatorResponse>> addNewExperience(@RequestPart("file") MultipartFile file) {

        try {
            StreamingLocatorResponse streamingLocatorResponse = azureMediaServiceService.uploadAndGetStreamingLocator(file.getBytes(),file.getName());
            return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,streamingLocatorResponse));
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

        return ResponseEntity.ok().body(new APIResponse<>("Request Failed",false,null));

    }


    @PostMapping(value = "/v1/video_stream/upload_from_url")
    public ResponseEntity<APIResponse<StreamingLocatorResponse>> uploadFileFromUrl(@RequestParam String url) {

        try {
            StreamingLocatorResponse streamingLocatorResponse = azureMediaServiceService.uploadAndGetStreamingLocatorFromURL(url);
            return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,streamingLocatorResponse));
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

        return ResponseEntity.ok().body(new APIResponse<>("Request Unsuccessful",false,null));
    }
}
