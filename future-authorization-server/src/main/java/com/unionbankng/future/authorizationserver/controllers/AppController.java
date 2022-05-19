package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.entities.Tag;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.services.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class AppController {

    private final SecurityService securityService;

    @PostMapping("/v1/email/test/forgot_password")
    public ResponseEntity<APIResponse<Tag>> testForgotPassword(@RequestParam String email) {
        securityService.initiateForgotPassword(email);
        return ResponseEntity.ok().body(new APIResponse<>("Request Successful",true,null));
    }
}
