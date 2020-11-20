package com.unionbankng.future.futurebankservice.services;

import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.retrofitservices.UBNNewAccountOpeningAPIService;
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
public class UBNNewAccountOpeningAPIServiceHandler {

    Logger logger = LoggerFactory.getLogger(UBNNewAccountOpeningAPIServiceHandler.class);

    @Value("${unionbankng.base.url}")
    private String ubnBaseURL;

    @Value("#{${unionbankng.credentials}}")
    private Map<String, String> credentials;

    private UBNNewAccountOpeningAPIService ubnAccountAPIService;

    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    @PostConstruct
    public void init() {

        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ubnBaseURL)
                .build();

        ubnAccountAPIService= retrofit.create(UBNNewAccountOpeningAPIService.class);

    }



    public UBNAuthServerTokenResponse getUBNAccountServerToken() throws IOException {

        Call<UBNAuthServerTokenResponse> responseCall =  ubnAccountAPIService.getAuthServerToken(credentials.get("username"),credentials.get("password"),credentials.get("clientSecret"),
                credentials.get("grantType"),credentials.get("clientId"));

        return  responseCall.execute().body();

    }


    public Response<AccountIdTypesResponse> getSupportedIdTypesForAccount() throws IOException {

        UBNAuthServerTokenResponse response = getUBNAccountServerToken();

        logger.info("Auth token response is : {}",response);

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getSupportedIdCardTypes(authorization,"01").execute();

    }

    public Response<AccountProductTypeResponse> getAccountProductTypes(AccountProductTypeRequest request) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAccountServerToken();

        logger.info("Auth token response is : {}",response);

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getProductTypes(authorization,"01",request).execute();

    }

    public Response<ProductDetailsResponse> getAccountProductDetails(String productCode) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAccountServerToken();

        logger.info("Auth token response is : {}",response);

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getProductDetails(
                authorization,"01",productCode).execute();

    }

    public Response<UBNCountryResponse> getCountriesForAccount() throws IOException {

        UBNAuthServerTokenResponse response = getUBNAccountServerToken();

        logger.info("Auth token response is : {}",response);

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getCountriesForAccount(
                authorization,"01").execute();

    }

    public Response<UBNStateByCountryResponse> getStatesByCountryForAccount(String countryCode) throws IOException {

        UBNAuthServerTokenResponse response = getUBNAccountServerToken();

        logger.info("Auth token response is : {}",response);

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getStatesByCountryForAccount(
                authorization,"01",countryCode).execute();

    }








}
