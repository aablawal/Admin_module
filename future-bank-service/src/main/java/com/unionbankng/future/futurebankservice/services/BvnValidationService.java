package com.unionbankng.future.futurebankservice.services;

import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.util.App;
import com.unionbankng.future.futurebankservice.util.JWTUserDetailsExtractor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Response;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class BvnValidationService {

    Logger logger = LoggerFactory.getLogger(BvnValidationService.class);
    private final App app;
    private final UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;

    private final FutureAuthServiceHandler futureAuthServiceHandler;

    @Value("${kula.base-url}")
    private String kulaBaseUrl;

    @Autowired
    RestTemplate restTemplate;

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

    public ResponseEntity<APIResponse<BVNVerificationResponse>> verifyCustomerBVN(String authToken, OAuth2Authentication authentication, String bvn, String otp, String dob) throws IOException {

        VerifyBvnRequest request = new VerifyBvnRequest();
        request.setBvn(bvn);
        request.setOtp(otp);

        app.print("###Verifying customer BVN");
        Response<BVNVerificationResponse> response = ubnAccountAPIServiceHandler.verifyCustomerBVN(request);

        if (response.isSuccessful() && response.body().getData()!=null) {
            app.print("Success block");
            app.print(response.body().getData());
            ValidateBvnResponse bvnData= response.body().getData();
            dob = dob == null ? bvnData.getDateOfBirth() : dob;
            initiateKYC(authToken, bvn, dob);

            JwtUserDetail currentUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(authentication);

            UserNameUpdateRequest updateRequest = new UserNameUpdateRequest();
            updateRequest.setFirstName(app.toTitleCase(bvnData.getFirstName()));
            updateRequest.setLastName(app.toTitleCase(bvnData.getLastName()));

            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                HttpEntity<UserNameUpdateRequest> entity = new HttpEntity<>(updateRequest, headers);

                restTemplate.exchange(
                        kulaBaseUrl + "/v1/users/" + currentUser.getUserUUID() + "/update_profile_details", HttpMethod.POST, entity, String.class).getBody();
                app.print("User name updated with name on their BVN");

            }catch (Exception ex){
                ex.printStackTrace();
            }

            return ResponseEntity.ok().body(new APIResponse<>(response.message(), true, response.body()));
        } else {
            app.print("Failure block");
            return ResponseEntity.ok().body(new APIResponse<>(response.body()!=null?response.body().getStatusMessage():"Unable to Verify BVN", false, null));
        }
    }

    @Async("initiateKycExecutor")
    void initiateKYC(String authToken, String bvn, String dob)  {
        app.print("###Async Initiating KYC");
        app.print("###Auth Token: " + authToken);
        app.print("###BVN: " + bvn);
        app.print("###DOB: " + (dob == null ? "null" : dob));
        futureAuthServiceHandler.initiateKYC(authToken, bvn, dob);
    }

}
