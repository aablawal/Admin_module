package com.unionbankng.future.futurebankservice.controllers;

import com.unionbankng.future.futurebankservice.entities.CustomerBankAccount;
import com.unionbankng.future.futurebankservice.enums.AccountStatus;
import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.services.CustomerBankAccountService;
import com.unionbankng.future.futurebankservice.services.UBNAccountAPIServiceHandler;
import com.unionbankng.future.futurebankservice.util.App;
import com.unionbankng.future.futurebankservice.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import retrofit2.Response;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class CustomerBankAccountController {

    private final CustomerBankAccountService customerBankAccountService;
    private final UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;

    private final App app;


    @PostMapping("/v1/ubn-account/add_bank_account")
    public ResponseEntity<APIResponse<CustomerBankAccount>> addBankAccount(OAuth2Authentication authentication, @RequestBody CustomerBankAccount bankAccount) {

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        if (customerBankAccountService.existsByAccountNumber(bankAccount.getAccountNumber()))
            return ResponseEntity.ok().body(new APIResponse<>("You have Already Added an Account", false, null));

        bankAccount.setAccountStatus(AccountStatus.PAYMENT_CONFIRMED);
        bankAccount.setUserUUID(jwtUserDetail.getUserUUID());

        CustomerBankAccount savedAccount= customerBankAccountService.save(bankAccount);
        if(savedAccount!=null)
          return ResponseEntity.ok().body(new APIResponse<>("Request Successful", true, savedAccount));
        else
            return ResponseEntity.ok().body(new APIResponse<>("Unable to save Bank Account", false, null));
    }


    @GetMapping("/v1/ubn-account/get_customer_bank_accounts")
    public ResponseEntity<APIResponse<List<CustomerBankAccount>>> getCustomerAccounts(@ApiIgnore OAuth2Authentication authentication) {
        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        List<CustomerBankAccount> accountList = customerBankAccountService.findAllByUserUUID(jwtUserDetail.getUserUUID());
        return ResponseEntity.ok().body(new APIResponse<>("Request Successful", true, accountList));
    }

    @GetMapping("/v1/ubn-account/get_accounts_by_mobile_number")
    public ResponseEntity<APIResponse<UBNGetAccountsResponse>> getAccountsByMobileNumber(@RequestParam String number) throws IOException {

        UBNGetAccountsRequest request = new UBNGetAccountsRequest();
        request.setMobileNumber(number);
        Response<UBNGetAccountsResponse> responseResponse = ubnAccountAPIServiceHandler.getAccountsByMobileNumber(request);
        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>(responseResponse.body() !=null?responseResponse.body().getMessage():"Request Failed", false, null));

        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

}
