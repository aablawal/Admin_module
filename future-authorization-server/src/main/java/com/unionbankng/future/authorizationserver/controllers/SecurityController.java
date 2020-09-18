package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.services.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class SecurityController {


    private  final SecurityService securityService;

    @PostMapping("v1/security/initiate_forgot_password")
    public ResponseEntity<APIResponse> initiateForgotPassword(@RequestParam String identifier){

        securityService.initiateForgotPassword(identifier);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",true,null));
    }

    @GetMapping("v1/security/confirm_reset_token")
    public ResponseEntity<APIResponse> confirmResetToken(@RequestParam String token){

        Boolean isSuccess = securityService.confirmForgotPasswordToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(
                new APIResponse("Request successful",isSuccess,null));
    }

    @PostMapping("v1/security/reset_password")
    public ResponseEntity<APIResponse> resetPassword(@NotNull  @RequestParam String token, @NotNull @RequestParam String password){

        return securityService.resetPassword(token,password);

    }
}
