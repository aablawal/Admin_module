package com.unionbankng.future.futurebankservice.controllers;


import com.unionbankng.future.futurebankservice.entities.CustomerBankAccount;
import com.unionbankng.future.futurebankservice.enums.AccountStatus;
import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.services.CustomerBankAccountService;
import com.unionbankng.future.futurebankservice.services.UBNNewAccountOpeningAPIServiceHandler;
import com.unionbankng.future.futurebankservice.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Response;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class UBNAccountOpeningController {

    Logger logger = LoggerFactory.getLogger(UBNAccountOpeningController.class);

    private final UBNNewAccountOpeningAPIServiceHandler ubnNewAccountOpeningAPIServiceHandler;
    private final CustomerBankAccountService customerBankAccountService;

    @GetMapping("/v1/ubn_account_opening/get_supported_id_types")
    public ResponseEntity<APIResponse<AccountIdTypesResponse>> getSupportedIdTypesForAccount() throws IOException {

        //determine existing or non existing customer
        Response<AccountIdTypesResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getSupportedIdTypesForAccount();

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @PostMapping("/v1/ubn_account_opening/get_account_product_types")
    public ResponseEntity<APIResponse<AccountProductTypeResponse>> getAccountProductTypes(@RequestBody AccountProductTypeRequest request) throws IOException {

        //determine existing or non existing customer
        Response<AccountProductTypeResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getAccountProductTypes(request);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/get_product_details_for_account")
    public ResponseEntity<APIResponse<ProductDetailsResponse>> getAccountProductDetails(@RequestParam String productCode) throws IOException {

        //determine existing or non existing customer
        Response<ProductDetailsResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getAccountProductDetails(productCode);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/get_countries_for_account")
    public ResponseEntity<APIResponse<UBNCountryResponse>> getCountriesForAccount() throws IOException {

        //determine existing or non existing customer
        Response<UBNCountryResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getCountriesForAccount();

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/get_states_by_country")
    public ResponseEntity<APIResponse<UBNStateByCountryResponse>> getStatesByCountryForAccount(@RequestParam String countryCode) throws IOException {

        //determine existing or non existing customer
        Response<UBNStateByCountryResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getStatesByCountryForAccount(countryCode);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/get_cities_by_country_and_state")
    public ResponseEntity<APIResponse<UBNCitiesResponse>> getCitiesByCountryAndStateForAccount(@RequestParam String countryCode,
                                                                                               @RequestParam String stateCode) throws IOException {

        //determine existing or non existing customer
        Response<UBNCitiesResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getCitiesByCountryAndStateForAccount(countryCode,stateCode);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/get_ubn_branches")
    public ResponseEntity<APIResponse<UBNBranchesResponse>> getUBNBranches() throws IOException {

        //determine existing or non existing customer
        Response<UBNBranchesResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getUBNBranches();

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/get_ubn_genders")
    public ResponseEntity<APIResponse<AccountProductTypeResponse>> getUBNGenders() throws IOException {

        //determine existing or non existing customer
        Response<AccountProductTypeResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getUBNGenders();

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/get_ubn_marital_status")
    public ResponseEntity<APIResponse<AccountProductTypeResponse>> getMaritalStatus() throws IOException {

        //determine existing or non existing customer
        Response<AccountProductTypeResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getMaritalStatus();

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/get_account_customer_types")
    public ResponseEntity<APIResponse<UBNCustomerTypeRequest>> getCustomerTypes() throws IOException {

        //determine existing or non existing customer
        Response<UBNCustomerTypeRequest> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getCustomerTypes();

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/get_account_types")
    public ResponseEntity<APIResponse<UBNAccountTypeResponse>> getUBNAccountTypes() throws IOException {

        //determine existing or non existing customer
        Response<UBNAccountTypeResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getUBNAccountTypes();

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/get_customer_segments")
    public ResponseEntity<APIResponse<UBNCustomerSegmentResponse>> getCustomersSegment() throws IOException {

        //determine existing or non existing customer
        Response<UBNCustomerSegmentResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getCustomersSegment();

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }


    @PostMapping("/v1/ubn_account_opening/create_new_customer_account")
    public ResponseEntity<APIResponse<UBNCreateAccountNewCustomerResponse>> createUBNNewCustomerAccount(@RequestBody UBNCreateAccountNewCustomerRequest request) throws IOException {

        //determine existing or non existing customer
        Response<UBNCreateAccountNewCustomerResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.createUBNNewCustomerAccount(request);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true,responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/get_document_types")
    public ResponseEntity<APIResponse<AccountProductTypeResponse>> getDocumentTypes(@RequestParam String productCode) throws IOException {

        //determine existing or non existing customer
        Response<AccountProductTypeResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.getDocumentTypes(productCode);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @PostMapping(value = "/v1/ubn_account_opening/upload_document/{type}/{recordId}",consumes = { "multipart/form-data" })
    public ResponseEntity<APIResponse<UBNGenericResponse>> uploadDocumentForAccount(@PathVariable Integer type,
                                                                                            @PathVariable Long recordId, @RequestPart("file") MultipartFile file) throws IOException {

        //determine existing or non existing customer
        Response<UBNGenericResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.uploadDocumentForAccount(recordId,type,file);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>(responseResponse.message(), false, null));

        //update customer account with status upload complete


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @PostMapping("/v1/ubn_account_opening/submit_document")
    public ResponseEntity<APIResponse<UBNGenericResponse>> submitDocumentsForAccount(
            @RequestParam String bvn, @RequestParam Long recordId
    ) throws IOException {

        //determine existing or non existing customer
        Response<UBNGenericResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.submitDocumentsForAccount(recordId,bvn);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer account with status upload complete


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @PostMapping("/v1/ubn_account_opening/initiate_payment")
    public ResponseEntity<APIResponse<UBNAccountPaymentResponse>> accountPaymentUBN1(
            @RequestParam Long customerId, @RequestParam Boolean isReactivated,@RequestBody UBNAccountPaymentRequest request
    ) throws IOException {

        //determine existing or non existing customer
        Response<UBNAccountPaymentResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.accountPaymentUBN1(customerId,isReactivated,request);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @PostMapping("/v1/ubn_account_opening/initiate_payment_second_leg")
    public ResponseEntity<APIResponse<UBNAccountPaymentResponse>> accountPaymentUBN1(
            @RequestParam Long customerId, @RequestParam String oldAccount,@RequestBody UBNAccountPaymentRequest request
    ) throws IOException {

        //determine existing or non existing customer
        Response<UBNAccountPaymentResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.accountPaymentUBN2(customerId,oldAccount,request);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @GetMapping("/v1/ubn_account_opening/confirm_payment_status")
    public ResponseEntity<APIResponse<UBNGenericResponse>> confirmUBNPaymentStatus(
            @RequestParam Long customerId
    ) throws IOException {

        //determine existing or non existing customer
        Response<UBNGenericResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.confirmUBNPaymentStatus(customerId);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        //update customer details with account number


        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

    @PostMapping("/v1/ubn_account_opening/complete_account_opening")
    public ResponseEntity<APIResponse<UBNCompleteAccountPaymentResponse>> completeUBNAccountCreation(
            @RequestBody @Valid CompleteUBNAccountCreationRequest request, @ApiIgnore OAuth2Authentication authentication
    ) throws IOException {

        JwtUserDetail jwtUserDetail = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);
        //determine existing or non existing customer
        Response<UBNCompleteAccountPaymentResponse> responseResponse = ubnNewAccountOpeningAPIServiceHandler.completeUBNAccountCreation(request);

        if(!responseResponse.isSuccessful())
            return ResponseEntity.status(responseResponse.code()).body(new APIResponse<>("An error occurred", false, null));

        UBNCompleteAccountPaymentResponse response = responseResponse.body();
        //update customer details with account number
        String accNumber = response.getData().split(":")[1].strip().replace("\"","");
        logger.info("Account Number is :{}",accNumber);

        CustomerBankAccount customerBankAccount = new CustomerBankAccount();
        customerBankAccount.setAccountNumber(accNumber);
        customerBankAccount.setAccountType(request.getAccountType());
        customerBankAccount.setBranchCode(request.getBranchCode());
        customerBankAccount.setAccountName(request.getAccountName());
        customerBankAccount.setAccountStatus(AccountStatus.PAYMENT_CONFIRMED);
        customerBankAccount.setCustomerUBNId(request.getCustomerRecordId());
        customerBankAccount.setUserUUID(jwtUserDetail.getUserUUID());

        customerBankAccountService.save(customerBankAccount);

        return ResponseEntity.ok().body(new APIResponse<>("Request successful", true, responseResponse.body()));

    }

}
