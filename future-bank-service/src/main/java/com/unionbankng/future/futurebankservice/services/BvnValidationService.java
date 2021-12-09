package com.unionbankng.future.futurebankservice.services;

import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.util.App;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BvnValidationService {

    Logger logger = LoggerFactory.getLogger(BvnValidationService.class);
    private final App app;
    private final UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;


    public ResponseEntity<APIResponse<BVNValidationResponse>> validateCustomerBVN(String bvn, String dob) throws IOException {

        ValidateBvnRequest request = new ValidateBvnRequest();
        request.setBvn(bvn);
        request.setAccountTier("SA_040");
        request.setDob(dob);

        app.print("###Validating customer BVN");
        app.print("Request:");
        app.print(request);

        Response<BVNValidationResponse> response = ubnAccountAPIServiceHandler.validateCustomerBVN(request);

        logger.info("status: "+response.isSuccessful());
        logger.info("message: "+response.message());
        app.print("Response:");
        app.print(response);

        if (response.isSuccessful()) {
            return  ResponseEntity.ok().body(new APIResponse<>(response.message(), true, response.body()));
        }else{
            return ResponseEntity.ok().body(new APIResponse<>(response.body()!=null?response.body().getStatusMessage():"Unable to Validate BVN", false, null));
        }
    }

    public ResponseEntity<APIResponse<BVNVerificationResponse>> verifyCustomerBVN(String bvn,String otp) throws IOException {

        VerifyBvnRequest request = new VerifyBvnRequest();
        request.setBvn(bvn);
        request.setOtp(otp);

        app.print("###Verifying customer BVN");
        app.print("Request:");
        app.print(request);
        Response<BVNVerificationResponse> response = ubnAccountAPIServiceHandler.verifyCustomerBVN(request);

        logger.info("status: " + response.isSuccessful());
        logger.info("message: " + response.message());
        app.print("API Response:");
        app.print(response);
        app.print("RESPONSE BODY: " + response.body().toString());
        if (response.isSuccessful() && response.body().getData()!=null) {
            app.print("Success block");
            return ResponseEntity.ok().body(new APIResponse<>(response.message(), true, response.body()));
        } else {
            app.print("Failure block");
            return ResponseEntity.ok().body(new APIResponse<>(response.body()!=null?response.body().getStatusMessage():"Unable to Verify BVN", false, null));
        }
    }
}
