package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.enums.TagType;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.services.SecurityService;
import com.unionbankng.future.authorizationserver.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class AppController {

    private final SecurityService securityService;
    @PostMapping("/v1/email/test/forgot_password")
    public ResponseEntity<APIResponse<Tag>> testForgotPassword() {

        securityService.initiateForgotPassword("net.rabiualiyu@gmail.com");
        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,null));
    }
}