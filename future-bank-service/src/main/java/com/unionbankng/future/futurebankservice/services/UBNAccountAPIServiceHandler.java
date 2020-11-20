package com.unionbankng.future.futurebankservice.services;

import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.retrofitservices.UBNAccountAPIService;
import com.unionbankng.future.futurebankservice.util.UnsafeOkHttpClient;
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
public class UBNAccountAPIServiceHandler {

    Logger logger = LoggerFactory.getLogger(UBNAccountAPIServiceHandler.class);

    @Value("${unionbankng.base.url}")
    private String ubnBaseURL;

    @Value("#{${unionbankng.credentials}}")
    private Map<String, String> credentials;

    private UBNAccountAPIService ubnAccountAPIService;

    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    @PostConstruct
    public void init() {

        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ubnBaseURL)
                .build();

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

    public Response<ValidateBvnResponse> validateCustomerBVN(ValidateBvnRequest request) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAuthServerToken();

        logger.info("Auth token response is : {}",response);

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

        return ubnAccountAPIService.validateBVN(response.getAccess_token(),request).execute();

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

}
