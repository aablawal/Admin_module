package com.unionbankng.future.futurebankservice.controllers;

import com.unionbankng.future.futurebankservice.pojos.APIResponse;
import com.unionbankng.future.futurebankservice.pojos.SidekiqBVNValidationResponse;
import com.unionbankng.future.futurebankservice.pojos.ValidateBvnRequest;
import com.unionbankng.future.futurebankservice.pojos.ValidateBvnResponse;
import com.unionbankng.future.futurebankservice.services.BvnValidationService;
import com.unionbankng.future.futurebankservice.services.UBNAccountAPIServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class BVNController {

    private final BvnValidationService bvnValidationService;

    @PostMapping("/v1/bvn/get_bvn_details")
    public ResponseEntity<APIResponse<ValidateBvnResponse>> getCustomerBVNDetails(@RequestParam String bvn) throws IOException {
        return bvnValidationService.getCustomerBVNDetails(bvn);
    }

    @PostMapping("/v1/bvn/verify_bvn")
    public ResponseEntity<APIResponse<SidekiqBVNValidationResponse>> verifyCustomerBVN(@RequestParam String bvn, @RequestParam String dob) throws IOException {

       return bvnValidationService.verifyCustomerBVN(bvn,dob);

    }
}
