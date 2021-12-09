package com.unionbankng.future.futurebankservice.controllers;

import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.services.BvnValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class BVNController {

    private final BvnValidationService bvnValidationService;

    @PostMapping("/v1/bvn/validate_bvn")
    public ResponseEntity<APIResponse<ValidateBvnResponse>> validateCustomerBVN(@RequestParam String bvn, @RequestParam String dob) throws IOException {
       return bvnValidationService.validateCustomerBVN(bvn,dob);
    }

    @PostMapping("/v1/bvn/verify_bvn")
    public ResponseEntity<APIResponse<BVNVerificationResponse>> verifyCustomerBVN(@RequestParam String bvn, @RequestParam String otp) throws IOException {
        return bvnValidationService.verifyCustomerBVN(bvn,otp);
    }

}
