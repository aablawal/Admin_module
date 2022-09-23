package com.unionbankng.future.futurejobservice.controllers;

import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.BundleService;
import com.unionbankng.future.futurejobservice.util.App;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class BundleController {

    private final App app;
    private  final BundleService bundleService;
    Logger logger = LoggerFactory.getLogger(BundleController.class);

    @PostMapping(value="/v1/bundle/add",consumes = "multipart/form-data")
    public ResponseEntity<APIResponse> postABundle(OAuth2Authentication authentication, @Valid @RequestParam(value = "data", required=true) String bundleData,
                                                @RequestParam(value = "supportingFiles", required = false) MultipartFile[] supportingFiles) throws IOException {

        return ResponseEntity.ok().body(bundleService.postABundle(authentication,bundleData,supportingFiles));
    }

    @PostMapping(value="/v1/bundle/{bundle_id}/create/job")
    public ResponseEntity<APIResponse> postAJobWithBundle(OAuth2Authentication authentication,
                                                @PathVariable Long bundle_id){

        return ResponseEntity.ok().body(bundleService.postJobWithBundle(authentication,bundle_id));
    }

    @GetMapping("/v1/bundle/getbundle/{id}")
    public ResponseEntity<APIResponse> getBundleById(@PathVariable Long id){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,bundleService.findBundleById(id)));
    }

    @GetMapping("/v1/bundle/getbundles")
    public ResponseEntity<APIResponse> getBundles(@PathVariable int page, int size){
        return ResponseEntity.ok().body(
                new APIResponse("success",true,bundleService.findAllBundles(page, size)));
    }

}
