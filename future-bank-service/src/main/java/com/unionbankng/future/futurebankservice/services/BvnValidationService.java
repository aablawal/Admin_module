package com.unionbankng.future.futurebankservice.services;

import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.util.App;
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
    private final App app;


    private final UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;

    public ResponseEntity<APIResponse<ValidateBvnResponse>> getCustomerBVNDetails(String bvn) throws IOException {

        ValidateBvnRequest request = new ValidateBvnRequest();
        request.setBvn(bvn);

        Response<ValidateBvnResponse> response = ubnAccountAPIServiceHandler.validateCustomerBVN(request);

        app.print(response);
        logger.info("status: "+response.isSuccessful());
        logger.info("message: "+response.message());
        if (!response.isSuccessful())
            return ResponseEntity.status(response.code()).body(new APIResponse<>(response.message(), false, null));

        return ResponseEntity.ok().body(new APIResponse<>("Request Successful", true, response.body()));
    }


    public ResponseEntity<APIResponse<ValidateBvnResponse>> validateCustomerBVN(String bvn,String dob) throws IOException {

        ValidateBvnRequest request = new ValidateBvnRequest();
        request.setBvn(bvn);
        request.setAccountTier("SA_040");
        request.setDob(dob);

        app.print("###Validating customer BVN");
        app.print("Request:");
        app.print(request);

        Response response = ubnAccountAPIServiceHandler.validateCustomerBVN(request);

        logger.info("status: "+response.isSuccessful());
        logger.info("message: "+response.message());
        app.print("Response:");
        app.print(response);

        if (response.isSuccessful()) {
              ResponseEntity<APIResponse<ValidateBvnResponse>> bvnDetails=this.getCustomerBVNDetails(bvn);
            return bvnDetails;
        }else{
            return ResponseEntity.ok().body(new APIResponse<>(response.message(), true, null));
        }
    }

    public ResponseEntity<APIResponse<ValidateBvnResponse>> verifyCustomerBVN(String bvn,String otp) throws IOException {

        VerifyBvnRequest request = new VerifyBvnRequest();
        request.setBvn(bvn);
        request.setOtp(otp);

        app.print("###Verifying customer BVN");
        app.print("Request:");
        app.print(request);
        Response response = ubnAccountAPIServiceHandler.verifyCustomerBVN(request);

        logger.info("status: " + response.isSuccessful());
        logger.info("message: " + response.message());
        app.print("Response:");
        app.print(response);
        if (response.isSuccessful()) {
            ResponseEntity<APIResponse<ValidateBvnResponse>> bvnDetails = this.getCustomerBVNDetails(bvn);
            return bvnDetails;
        } else {
            return ResponseEntity.ok().body(new APIResponse<>(response.message(), true, null));
        }
    }
}
