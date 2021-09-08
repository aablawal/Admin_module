package com.unionbankng.future.authorizationserver.controllers;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.services.PinService;
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
public class PinController {

    private final PinService pinService;

    @PostMapping("/v1/pin/set")
    public ResponseEntity<APIResponse> createNewPin(Principal principal, @RequestParam String pin) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok().body(pinService.createPin(principal, pin));
    }


    @PostMapping("/v1/pin/verify")
    public ResponseEntity<APIResponse> verifyPin(Principal principal, @RequestParam String pin) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok().body(pinService.verifyPin(principal, pin));
    }


}
