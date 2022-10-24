package com.unionbankng.future.futurebankservice.services;

import com.unionbankng.future.futurebankservice.pojos.*;
import com.unionbankng.future.futurebankservice.retrofitservices.UBNNewAccountOpeningAPIService;
import com.unionbankng.future.futurebankservice.util.App;
import com.unionbankng.future.futurebankservice.util.UnsafeOkHttpClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    private UBNNewAccountOpeningAPIService ubnAccountAPIService;
    @Autowired
    private  App app;
    @Autowired
    private  UBNAuthService authService;

    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    @PostConstruct
    public void init() {

        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ubnBaseURL)
                .build();

        ubnAccountAPIService= retrofit.create(UBNNewAccountOpeningAPIService.class);

    }
    

    public Response<AccountIdTypesResponse> getSupportedIdTypesForAccount() throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        logger.info("access token is : {}",response.getAccess_token());

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getSupportedIdCardTypes(authorization,"01").execute();

    }

    public Response<AccountProductTypeResponse> getAccountProductTypes(AccountProductTypeRequest request) throws IOException {

        app.print("Getting account types... ");
        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        Response<AccountProductTypeResponse>  responseResponse= ubnAccountAPIService.getProductTypes(authorization,"01",request).execute();
        app.print(responseResponse.code());
        return  responseResponse;
    }

    public Response<ProductDetailsResponse> getAccountProductDetails(String productCode) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getProductDetails(
                authorization,"01",productCode).execute();

    }

    public Response<UBNCountryResponse> getCountriesForAccount() throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getCountriesForAccount(
                authorization,"01").execute();

    }

    public Response<UBNStateByCountryResponse> getStatesByCountryForAccount(String countryCode) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getStatesByCountryForAccount(
                authorization,"01",countryCode).execute();

    }

    public Response<UBNCitiesResponse> getCitiesByCountryAndStateForAccount(String countryCode, String stateCode) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getCitiesByCountryAndStateForAccount(
                authorization,"01",countryCode,stateCode).execute();

    }

    public Response<UBNBranchesResponse> getUBNBranches() throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getUBNBranches(
                authorization,"01").execute();

    }

    public Response<AccountProductTypeResponse> getUBNGenders() throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getUBNGenders(
                authorization,"01").execute();

    }

    public Response<AccountProductTypeResponse> getMaritalStatus() throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getMaritalStatus(
                authorization,"01").execute();

    }

    public Response<UBNCustomerTypeRequest> getCustomerTypes() throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getCustomerTypes(
                authorization,"01").execute();

    }

    public Response<UBNAccountTypeResponse> getUBNAccountTypes() throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getUBNAccountTypes(
                authorization,"01").execute();

    }

    public Response<UBNCustomerSegmentResponse> getCustomersSegment() throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getCustomersSegment(
                authorization,"01").execute();

    }

    public Response<UBNCustomerTypeRequest> getSourceOfFund() throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getSourceOfFund(
                authorization,"01").execute();

    }


    public Response<UBNCreateAccountNewCustomerResponse> createUBNNewCustomerAccount(UBNCreateAccountNewCustomerRequest request) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        app.print("#################################Account Opening initiated");

        String authorization = String.format("Bearer %s",response.getAccess_token());
        request.setAnnualIncome("600000");
        request.setSourceOfIncome("work");
        request.setProductCode("SA_040");

        Response<UBNCreateAccountNewCustomerResponse> responseData=ubnAccountAPIService.createUBNNewCustomerAccount(
                authorization,"01",request).execute();

        app.print(responseData.message());
        app.print(responseData.isSuccessful());

        app.print("Done");
        return  responseData;
    }


    public Response<AccountProductTypeResponse> getDocumentTypes(String productCode) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getDocumentTypes(
                authorization,"01",productCode).execute();

    }

    public Response<UBNGenericResponse> uploadDocumentForAccount(Long recordId, Integer type, MultipartFile file) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        RequestBody requestFile =
                RequestBody.create(MediaType.get(file.getContentType()),
                        file.getBytes()
                );

        MultipartBody.Part fileData =
                MultipartBody.Part.createFormData("stream", file.getOriginalFilename(), requestFile);


        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.uploadDocumentForAccount(
                authorization,"01",recordId,type,fileData).execute();

    }

    public Response<UBNGenericResponse> submitDocumentsForAccount(Long recordId, String bvn) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.submitDocumentsForAccount(
                authorization,"01",bvn,recordId).execute();

    }

    public Response<UBNAccountPaymentResponse> accountPaymentUBN1(Long customerId, Boolean isReactivated, UBNAccountPaymentRequest request) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.accountPaymentUBN1(
                authorization,"01",customerId,isReactivated,request).execute();

    }

    public Response<UBNAccountPaymentResponse> accountPaymentUBN2(Long customerId, String oldAccount, UBNAccountPaymentRequest request) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.accountPaymentUBN2(
                authorization,"01",customerId,oldAccount,request).execute();

    }

    public Response<UBNGenericResponse> confirmUBNPaymentStatus(Long customerId) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.confirmUBNPaymentStatus(
                authorization,"01",customerId).execute();

    }

    public Response<UBNCompleteAccountPaymentResponse> completeUBNAccountCreation(UBNAccountCreationRequest request) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        app.print("@ACCOUNT OPENING");
        app.print("Request:");

        try {
            String authorization = String.format("Bearer %s", response.getAccess_token());
            Response<UBNCompleteAccountPaymentResponse> ubnResponse = ubnAccountAPIService.completeUBNAccountCreation(authorization, "01", request).execute();
            app.print("Response:");
            app.print(ubnResponse.body() == null ? "ubnResponse.body() = null" : ubnResponse.body());
            app.print(ubnResponse.body() == null ? "ubnResponse.body() = null" : ubnResponse.body().getStatusMessage());
            return  ubnResponse;
            
        }
        catch ( Exception ex){
            app.print("Failed:");
            ex.printStackTrace();
            return  null;
        }

    }

    public Response<UBNAccountDataResponse> getUBNAccountDetails(Long accountId) throws IOException {

        UBNAuthServerTokenResponse response = authService.getUBNAccountServerToken();

        if(response == null)
            return null;

        String authorization = String.format("Bearer %s",response.getAccess_token());
        return ubnAccountAPIService.getUBNAccountDetails(
                authorization,"01",accountId).execute();

    }










}
