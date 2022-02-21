package com.unionbankng.future.futurebankservice.services;

import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.retrofitservices.UBNAccountAPIService;
import com.unionbankng.future.futurebankservice.util.App;
import com.unionbankng.future.futurebankservice.util.UnsafeOkHttpClient;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RefreshScope
@Service
@RequiredArgsConstructor
public class UBNAccountAPIServiceHandler {

    Logger logger = LoggerFactory.getLogger(UBNAccountAPIServiceHandler.class);

   @Value("${unionbankng.base.url}")
    private String ubnBaseURL;

   @Value("#{${unionbankng.credentials}}")
    private Map<String, String> credentials;



    private UBNAccountAPIService ubnAccountAPIService;
    private final App app;

    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ubnBaseURL)
                .build();
            logger.info("URL:"+ubnBaseURL);
        ubnAccountAPIService= retrofit.create(UBNAccountAPIService.class);
    }


       public UBNAuthServerTokenResponse getUBNAuthServerToken() throws IOException {
        logger.info("Fetching Auth Server Token");

        Call<UBNAuthServerTokenResponse> responseCall = ubnAccountAPIService.getAuthServerToken(credentials.get("username"),
                credentials.get("password"),credentials.get("clientSecret"), credentials.get("grantType"),
                credentials.get("clientId"));

        app.print("responseCall Request url:");
        app.print(responseCall.request().url().url());

        UBNAuthServerTokenResponse response=  responseCall.execute().body();
        app.print("/authserv/oauth/token is :"+ (response != null ? response.getAccess_token() : "null"));
        return response;
    }

    public UBNAuthServerTokenResponse getUBNAccountServerToken() throws IOException {

//        credentials.put("clientSecret","KULAUSERCLIENT");
//        credentials.put("clientId","KULAUSER");
//        credentials.put("grantType","password");
//        credentials.put("username","kulasuser");
//        credentials.put("password","JqOmrhC5Cs");

        logger.info("Fetching Account Server Token");


        Call<UBNAuthServerTokenResponse> responseCall =  ubnAccountAPIService.getAccountServerToken(credentials.get("username"),credentials.get("password"),credentials.get("clientSecret"),
                credentials.get("grantType"),credentials.get("clientId"));
        app.print(responseCall);
        UBNAuthServerTokenResponse response=  responseCall.execute().body();
        app.print("/ubnmiserv/oauth/token is :"+ (response != null ? response.getAccess_token() : null));
        return  response;
    }
    
    public Response<BVNValidationResponse> validateCustomerBVN(ValidateBvnRequest request) throws IOException {

        logger.info("Fetching BVN Validation");

        UBNAuthServerTokenResponse response = getUBNAuthServerToken();

        logger.info("Auth token response is : {}",response == null ? "null" : response);
        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());
        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.validateBVN(authorization,"01",request).execute();

    }

    public Response<BVNVerificationResponse> verifyCustomerBVN(VerifyBvnRequest request) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAuthServerToken();

        logger.info("Auth token response is : {}",response);
        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.verifyBVN(authorization,"01",request).execute();

    }

   public Response<UBNGetAccountsResponse> getAccountsByMobileNumber(UBNGetAccountsRequest request) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAccountServerToken();

        if(response == null)
            return null;
        app.print("Get accounts nby mobile number");
        app.print("/ubnmiserv/oauth/token is :"+response.getAccess_token());
        app.print("Request:");
        app.print(request);

        Response<UBNGetAccountsResponse>  responseResponse= ubnAccountAPIService.getAccountsByMobileNumber(
                response.getAccess_token(),request).execute();

        app.print("Response:");
        app.print(responseResponse.code());
        app.print(responseResponse.body());
        return  responseResponse;
    }



    public Response<UBNCreateAccountResponse> openAccountForNewCustomer(UBNCreateAccountNewCustomerRequest request) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAccountServerToken();

        logger.info("Auth token response is : {}",response);

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

        return ubnAccountAPIService.openAccount(response.getAccess_token(),request).execute();

    }

    public Response<UBNCreateAccountResponse> openAccountForExistingCustomer(UBNOpenAccountExistingCustomerRequest request) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAccountServerToken();

        logger.info("Auth token response is : {}",response);

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

        return ubnAccountAPIService.openAccountExistingCustomer(response.getAccess_token(),request).execute();

    }

    public Response<UBNFundTransferResponse> transferFundsUBN(UBNFundTransferRequest request) throws IOException {
        UBNAuthServerTokenResponse response = getUBNAccountServerToken();
        app.print("############Funds Transfer Started");
        logger.info("Auth token response is : {}",response);
        if(response == null)
            return null;
        logger.info("access token is : {}",response.getAccess_token());
        app.print("Request:");
        app.print(request);
        Response<UBNFundTransferResponse> responseResponse=  ubnAccountAPIService.fundsTransferUBN(response.getAccess_token(),request).execute();
        app.print("Response:");
        app.print(responseResponse.body());
        return  responseResponse;
    }

    public Response<UBNBulkFundTransferResponse> transferBulkFundsUBN(UBNBulkFundTransferRequest request) throws IOException {
        UBNAuthServerTokenResponse response = getUBNAccountServerToken();
        logger.info("Auth token response is : {}",response);
        if(response == null)
            return null;
        logger.info("access token is : {}",response.getAccess_token());
        app.print("Request:");
        app.print(request);
        Response<UBNBulkFundTransferResponse> responseResponse= ubnAccountAPIService.bulkFundsTransferUBN(response.getAccess_token(),request).execute();
        app.print("Response:");
        app.print(responseResponse.body());
        app.print(responseResponse.code());
        return  responseResponse;
    }

    public Response<UbnCustomerAccountEnquiryResponse> accountEnquiry(UbnCustomerEnquiryRequest request) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAccountServerToken();

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());
        app.print(request);

        return ubnAccountAPIService.accountEnquiry(response.getAccess_token(),request).execute();

    }

    public Response<UBNAccountStatementResponse> accountStatement(UBNAccountStatementRequest request) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAccountServerToken();

        logger.info("Auth token response is : {}",response);

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

        return ubnAccountAPIService.accountStatement(response.getAccess_token(),request).execute();

    }


    //Create endpoint for fetching bank account

}
