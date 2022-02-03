package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.ChangePasswordRequest;
import com.unionbankng.future.authorizationserver.pojos.ResetPassword;
import com.unionbankng.future.authorizationserver.services.SecurityService;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class SecurityController {


    private  final SecurityService securityService;
    private final App app;

    @PostMapping("v1/security/initiate_forgot_password")
    public ResponseEntity<APIResponse> initiateForgotPassword(@RequestParam String identifier){

        app.print("Initiatinf Forgot password");
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

    //Reset user's password
    @PostMapping("v1/security/reset_password")
    public ResponseEntity<APIResponse> resetPassword(@RequestBody @Valid ResetPassword request){
        app.print("Resetting password");
        app.print(request.toString());
        return securityService.resetPassword(request.getToken(), request.getNewPassword());

    }

    @PostMapping("v1/security/change_password")
    public ResponseEntity<APIResponse> changePassword(@NotNull ChangePasswordRequest request){
        return securityService.changePassword(request);

    }


}
