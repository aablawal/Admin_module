package com.unionbankng.future.futurebankservice.controllers;


import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.services.UBNAccountAPIServiceHandler;
import com.unionbankng.future.futurebankservice.services.UBNResponseService;
import com.unionbankng.future.futurebankservice.util.App;
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
    private final UBNResponseService ubnResponseService;
    private final App app;

    @PostMapping("/v1/ubn_funds/transfer")
    public ResponseEntity<APIResponse<UBNFundTransferResponse>> transferFundsUBN(@RequestBody UBNFundTransferRequest request) throws IOException {
        //determine existing or non existing customer
        Response<UBNFundTransferResponse> responseResponse = ubnAccountAPIServiceHandler.transferFundsUBN(request);
//        String responseMessage=ubnResponseService.getResponseMessage(responseResponse.body().getCode(),responseResponse.body().getMessage());
        app.print(responseResponse.body());

        if(!responseResponse.body().getCode().equals("00"))
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred "+responseResponse.body().getMessage(), false, responseResponse.body()));
        //update customer details with account number
        return ResponseEntity.ok().body(new APIResponse<>("Funds transfer successful", true, responseResponse.body()));
    }

    @PostMapping("/v1/ubn_funds/bulk_transfer")
    public ResponseEntity<APIResponse<UBNBulkFundTransferResponse>> transferBulkFundsUBN(@RequestBody UBNBulkFundTransferRequest request) throws IOException {
        app.print(request);
        //determine existing or non existing customer
        Response<UBNBulkFundTransferResponse> responseResponse = ubnAccountAPIServiceHandler.transferBulkFundsUBN(request);
        app.print(responseResponse);
        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));
        //update customer details with account number
        return ResponseEntity.ok().body(new APIResponse<>("Funds transfer successful", true, responseResponse.body()));
    }

    @PostMapping("/v1/ubn/account_inquiry")
    public ResponseEntity<APIResponse<UbnEnquiryResponse>> accountEnquiry(@RequestBody UbnCustomerEnquiry request) throws IOException {

        //determine existing or non existing customer
        Response<UbnEnquiryResponse> responseResponse = ubnAccountAPIServiceHandler.accountEnquiry(request);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @PostMapping("/v1/ubn/account_statement")
    public ResponseEntity<APIResponse<UBNAccountStatementResponse>> accountStatement(@RequestBody UBNAccountStatementRequest request) throws IOException {

        //determine existing or non existing customer
        Response<UBNAccountStatementResponse> responseResponse = ubnAccountAPIServiceHandler.accountStatement(request);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }


}
