package com.unionbankng.future.futurebankservice.services;

import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.util.App;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BvnValidationService {

    Logger logger = LoggerFactory.getLogger(BvnValidationService.class);
    private final App app;
    private final UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;

    private final FutureAuthServiceHandler futureAuthServiceHandler;


    public ResponseEntity<APIResponse<BVNValidationResponse>> validateCustomerBVN(String bvn, String dob) throws IOException {

        ValidateBvnRequest request = new ValidateBvnRequest();
        request.setBvn(bvn);
        request.setAccountTier("SA_040");
        request.setDob(dob);

        Response<BVNValidationResponse> response = ubnAccountAPIServiceHandler.validateCustomerBVN(request);
        app.print("RESPONSE BODY: " + (response.body() != null ? response.body().getStatusMessage() : "Response body is null"));

        if (response.isSuccessful()) {
            app.print("Success block");
            return  ResponseEntity.ok().body(new APIResponse<>(response.body() != null ?
                    response.body().getStatusMessage() : "Response body is null", true, response.body()));
        }else{
            app.print("Failure block");
            return ResponseEntity.ok().body(new APIResponse<>("Unable to Validate BVN", false, null));
        }
    }

    public ResponseEntity<APIResponse<BVNVerificationResponse>> verifyCustomerBVN(String authToken, String bvn, String otp, String dob) throws IOException {

        VerifyBvnRequest request = new VerifyBvnRequest();
        request.setBvn(bvn);
        request.setOtp(otp);

        app.print("###Verifying customer BVN");
        Response<BVNVerificationResponse> response = ubnAccountAPIServiceHandler.verifyCustomerBVN(request);

        if (response.isSuccessful() && response.body().getData()!=null) {
            app.print("Success block");
            initiateKYC(authToken, bvn, dob);
            return ResponseEntity.ok().body(new APIResponse<>(response.message(), true, response.body()));
        } else {
            app.print("Failure block");
            return ResponseEntity.ok().body(new APIResponse<>(response.body()!=null?response.body().getStatusMessage():"Unable to Verify BVN", false, null));
        }
    }

//    @Async("initiateKycExecutor")
    void initiateKYC(String authToken, String bvn, String dob)  {
        app.print("###Async Initiating KYC");
        app.print("###Auth Token: " + authToken);
        app.print("###BVN: " + bvn);
        app.print("###DOB: " + (dob == null ? "null" : dob));
        futureAuthServiceHandler.initiateKYC(authToken, bvn, dob);
    }
}
