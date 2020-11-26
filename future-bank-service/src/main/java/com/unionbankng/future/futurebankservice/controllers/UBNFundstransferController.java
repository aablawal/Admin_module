package com.unionbankng.future.futurebankservice.controllers;


import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.services.UBNAccountAPIServiceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UBNFundstransferController {

    private final UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;

    @PostMapping("/v1/ubn_funds/transfer")
    public ResponseEntity<APIResponse<UBNFundTransferResponse>> transferFundsUBN(@RequestBody UBNFundTransferRequest request) throws IOException {

        //determine existing or non existing customer
        Response<UBNFundTransferResponse> responseResponse = ubnAccountAPIServiceHandler.transferFundsUBN(request);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Funds transfer successful", true, responseResponse.body()));

    }
}
