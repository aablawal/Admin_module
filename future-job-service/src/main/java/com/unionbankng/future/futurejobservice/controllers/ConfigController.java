package com.unionbankng.future.futurejobservice.controllers;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.services.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class ConfigController {

    private final ConfigService configService;

    @PostMapping("/v1/job/config/initialize_configs")
    public ResponseEntity<APIResponse<String>> resetConfigs(){
        return ResponseEntity.ok().body(configService.initializeConfigs());
    }

}
