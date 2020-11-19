package com.unionbankng.future.futurebankservice.controllers;


import com.unionbankng.future.futurebankservice.pojos.APIResponse;
import com.unionbankng.future.futurebankservice.pojos.SidekiqBVNValidationResponse;
import com.unionbankng.future.futurebankservice.pojos.UBNCreateAccountNewCustomerRequest;
import com.unionbankng.future.futurebankservice.pojos.UBNCreateAccountResponse;
import com.unionbankng.future.futurebankservice.services.UBNAccountAPIServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UBNAccountController {

    private final UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;

    @PostMapping("/v1/ubn_account/create_account_for_customer")
    public ResponseEntity<APIResponse<UBNCreateAccountResponse>> openAccountForCustomer(@RequestBody UBNCreateAccountNewCustomerRequest request) throws IOException {

        //determine existing or non existing customer
        Response<UBNCreateAccountResponse> responseResponse = ubnAccountAPIServiceHandler.openAccountForNewCustomer(request);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Account creation successful", true, responseResponse.body()));

    }
}
