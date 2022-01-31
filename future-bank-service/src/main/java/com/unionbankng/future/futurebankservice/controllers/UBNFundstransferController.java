package com.unionbankng.future.futurebankservice.controllers;


import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.services.UBNAccountAPIServiceHandler;
import com.unionbankng.future.futurebankservice.services.UBNResponseService;
import com.unionbankng.future.futurebankservice.util.App;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        if(responseResponse==null){
            if(responseResponse.body()==null){
                if(!responseResponse.body().getCode().equals("00")){
                    return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred "+responseResponse.body().getMessage(), false, responseResponse.body()));
                }
                return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("Unable to complete payment", false, responseResponse.body()));
            }
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("Unable to complete payment", false, null));
        }
        //update customer details with account number
        return ResponseEntity.ok().body(new APIResponse<>("Funds transfer successful", true, responseResponse.body()));
    }

    @PostMapping("/v1/ubn_funds/bulk_transfer")
    public ResponseEntity<APIResponse<UBNBulkFundTransferResponse>> transferBulkFundsUBN(@RequestBody UBNBulkFundTransferRequest request) throws IOException {
        app.print(request);
        Response<UBNBulkFundTransferResponse> responseResponse = ubnAccountAPIServiceHandler.transferBulkFundsUBN(request);
        app.print(responseResponse);
        if(responseResponse==null){
            if(responseResponse.body()==null){
                if(!responseResponse.body().getCode().equals("00")){
                    return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred "+responseResponse.body().getMessage(), false, responseResponse.body()));
                }
                return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("Unable to complete payment", false, responseResponse.body()));
            }
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("Unable to complete payment", false, null));
        }
        return ResponseEntity.ok().body(new APIResponse<>("Funds transfer successful", true, responseResponse.body()));
    }

    @PostMapping("/v1/ubn/account_inquiry")
    public ResponseEntity<APIResponse<UbnAccountEnquiryResponse>> accountEnquiry(@RequestBody UbnCustomerEnquiryRequest request) throws IOException {

        app.print("##########Starting Account Enquiry...");
        app.print("Request:");
        app.print(request);
        //determine existing or non existing customer
        Response<UbnCustomerAccountEnquiryResponse> responseResponse = ubnAccountAPIServiceHandler.accountEnquiry(request);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>(responseResponse.message(), false, null));

        //update customer details with account number
        app.print("Response:");
        app.print(responseResponse.body());

        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body().getAccount()));

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
