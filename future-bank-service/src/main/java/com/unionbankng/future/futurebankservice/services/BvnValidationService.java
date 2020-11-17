package com.unionbankng.future.futurebankservice.services;

import com.unionbankng.future.futurebankservice.pojos.APIResponse;
import com.unionbankng.future.futurebankservice.pojos.SidekiqBVNValidationResponse;
import com.unionbankng.future.futurebankservice.pojos.ValidateBvnRequest;
import com.unionbankng.future.futurebankservice.pojos.ValidateBvnResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import retrofit2.Response;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BvnValidationService {

    Logger logger = LoggerFactory.getLogger(BvnValidationService.class);


    private final UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;

    public ResponseEntity<APIResponse<ValidateBvnResponse>> getCustomerBVNDetails(String bvn) throws IOException {

        ValidateBvnRequest request = new ValidateBvnRequest();
        request.setBvn(bvn);


        Response<ValidateBvnResponse> response = ubnAccountAPIServiceHandler.validateCustomerBVN(request);

        if (!response.isSuccessful())
            return ResponseEntity.status(response.code()).body(new APIResponse<>("An error occured", false, null));

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful", true, response.body()));
    }

    /*
    dob format 25-Dec-94
     */
    public ResponseEntity<APIResponse<SidekiqBVNValidationResponse>> verifyCustomerBVN(String bvn,String dob) throws IOException {

        ValidateBvnRequest request = new ValidateBvnRequest();
        request.setBvn(bvn);

        Response<ValidateBvnResponse> response = ubnAccountAPIServiceHandler.validateCustomerBVN(request);



        if (!response.isSuccessful())
            return ResponseEntity.status(response.code()).body(new APIResponse<>("Network Error", true, null));

        logger.info("Bvn date of birth is :{}",response.body().getDateOfBirth());

        if (!response.body().getDateOfBirth().trim().equalsIgnoreCase(dob.trim()))
            return ResponseEntity.badRequest().body(new APIResponse<>("Validation failed", true, null));



        SidekiqBVNValidationResponse sidekiqBVNValidationResponse = new SidekiqBVNValidationResponse();
        sidekiqBVNValidationResponse.setFirstName(response.body().getFirstName());
        sidekiqBVNValidationResponse.setLastName(response.body().getLastName());
        sidekiqBVNValidationResponse.setDob(response.body().getDateOfBirth());
        sidekiqBVNValidationResponse.setPhoneNumber(response.body().getPhoneNumber());

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful", true, sidekiqBVNValidationResponse));

    }
}
