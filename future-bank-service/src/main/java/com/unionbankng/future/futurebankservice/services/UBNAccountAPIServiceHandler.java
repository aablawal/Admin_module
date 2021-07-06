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

        Call<UBNAuthServerTokenResponse> responseCall = ubnAccountAPIService.getAuthServerToken(credentials.get("username"),credentials.get("password"),credentials.get("clientSecret"),
                credentials.get("grantType"),credentials.get("clientId"));
        return  responseCall.execute().body();

    }

    public UBNAuthServerTokenResponse getUBNAccountServerToken() throws IOException {

        Call<UBNAuthServerTokenResponse> responseCall =  ubnAccountAPIService.getAccountServerToken(credentials.get("username"),credentials.get("password"),credentials.get("clientSecret"),
                credentials.get("grantType"),credentials.get("clientId"));
        return  responseCall.execute().body();

    }

    public Response<BVNValidationResponse> validateCustomerBVN(ValidateBvnRequest request) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAuthServerToken();

        logger.info("Auth token response is : {}",response);
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
        logger.info("Auth token response is : {}",response);
        if(response == null)
            return null;
        logger.info("access token is : {}",response.getAccess_token());
        return ubnAccountAPIService.fundsTransferUBN(response.getAccess_token(),request).execute();
    }

    public Response<UBNBulkFundTransferResponse> transferBulkFundsUBN(UBNBulkFundTransferRequest request) throws IOException {
        UBNAuthServerTokenResponse response = getUBNAccountServerToken();
        logger.info("Auth token response is : {}",response);
        if(response == null)
            return null;
        logger.info("access token is : {}",response.getAccess_token());
        Response<UBNBulkFundTransferResponse> response1= ubnAccountAPIService.bulkFundsTransferUBN(response.getAccess_token(),request).execute();
        app.print(response1.code());
        return  response1;
    }

    public Response<UbnEnquiryResponse> accountEnquiry(UbnCustomerEnquiry request) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAccountServerToken();

        logger.info("Auth token response is : {}",response);

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

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

}
