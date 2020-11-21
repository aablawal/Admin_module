package com.unionbankng.future.futurebankservice.controllers;

import com.unionbankng.future.futurebankservice.entities.CustomerBankAccount;
import com.unionbankng.future.futurebankservice.pojos.APIResponse;
import com.unionbankng.future.futurebankservice.pojos.JwtUserDetail;
import com.unionbankng.future.futurebankservice.services.CustomerBankAccountService;
import com.unionbankng.future.futurebankservice.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class CustomerBankAccountController {

    private final CustomerBankAccountService customerBankAccountService;

    @GetMapping("/v1/customer_bank_accounts")
    public ResponseEntity<APIResponse<List<CustomerBankAccount>>> getCustomerAccounts(@ApiIgnore OAuth2Authentication authentication) throws IOException {

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

        List<CustomerBankAccount> accountList = customerBankAccountService.findAllByUserUUID(jwtUserDetail.getUserUUID());

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful", true, accountList));


    }


}
