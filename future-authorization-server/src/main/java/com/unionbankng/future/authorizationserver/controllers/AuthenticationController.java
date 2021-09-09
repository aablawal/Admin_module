package com.unionbankng.future.authorizationserver.controllers;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/v1/pin/set")
    public ResponseEntity<APIResponse> createNewPin(Principal principal, @RequestParam String pin){
        return ResponseEntity.ok().body(authenticationService.createPin(principal, pin));
    }

    @PostMapping("/v1/pin/verify")
    public ResponseEntity<APIResponse> verifyPin(Principal principal, @RequestParam String pin){
        return ResponseEntity.ok().body(authenticationService.verifyPin(principal, pin));
    }

    @PostMapping("/v1/otp/generate")
    public ResponseEntity<APIResponse> generateOTP(Principal principal){
        return ResponseEntity.ok().body(authenticationService.generateOTP(principal));
    }


    @PostMapping("/v1/otp/verify")
    public ResponseEntity<APIResponse> verifyOTP(Principal principal, @RequestParam String otp){
        return ResponseEntity.ok().body(authenticationService.verifyOTP(principal, otp));
    }


}
