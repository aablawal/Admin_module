package com.unionbankng.future.futurebankservice.controllers;

import com.unionbankng.future.futurebankservice.entities.CustomerBankAccount;
import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.retrofitservices.UBNAccountAPIService;
import com.unionbankng.future.futurebankservice.services.CustomerBankAccountService;
import com.unionbankng.future.futurebankservice.services.UBNAccountAPIServiceHandler;
import com.unionbankng.future.futurebankservice.services.UBNNewAccountOpeningAPIServiceHandler;
import com.unionbankng.future.futurebankservice.util.App;
import com.unionbankng.future.futurebankservice.util.JWTUserDetailsExtractor;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Response;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class CustomerBankAccountController {

    private final App app;
    private final CustomerBankAccountService customerBankAccountService;
    private final UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;


    @GetMapping("/v1/customer_bank_accounts")
    public ResponseEntity<APIResponse<List<CustomerBankAccount>>> getCustomerAccounts(@ApiIgnore Principal principal) {

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(principal);
        List<CustomerBankAccount> accountList = customerBankAccountService.findAllByUserUUID(jwtUserDetail.getUserUUID());
        return ResponseEntity.ok().body(new APIResponse<>("Request Successful", true, accountList));

    }

    @GetMapping("/v1/ubn_account/get_accounts_by_mobile_number")
    public ResponseEntity<APIResponse<UBNGetAccountsResponse>> getAccountsByMobileNumber(@RequestParam String number) throws IOException {

        UBNGetAccountsRequest request = new UBNGetAccountsRequest();
        request.setMobileNumber(number);
        Response<UBNGetAccountsResponse> responseResponse = ubnAccountAPIServiceHandler.getAccountsByMobileNumber(request);
        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("Request Failed", false, null));

        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

}
