package com.unionbankng.future.futurebankservice.retrofitservices;

import com.unionbankng.future.futurebankservice.pojos.*;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface UBNNewAccountOpeningAPIService {

    @POST("authserv/oauth/token")
    Call<UBNAuthServerTokenResponse> getAuthServerToken(@Query("username") String username, @Query("password") String password,
                                                        @Query("client_secret") String clientSecret, @Query("grant_type") String grantType,
                                                        @Query("client_id") String clientId);
    @GET("account-service/account/id-card/type")
    Call<AccountIdTypesResponse> getSupportedIdCardTypes(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @POST("account-service/account/product")
    Call<AccountProductTypeResponse> getProductTypes(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                 @Body AccountProductTypeRequest request);

    @GET("account-service/account/product/details")
    Call<ProductDetailsResponse> getProductDetails(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                     @Query("productCode") String productCode);

    @GET("account-service/message/country")
    Call<UBNCountryResponse> getCountriesForAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @GET("account-service/message/country")
    Call<UBNStateByCountryResponse> getStatesByCountryForAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                          @Query("countryCode") String countryCode);

    @GET("account-service/message/country")
    Call<UBNCitiesResponse> getCitiesByCountryAndStateForAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                                 @Query("countryCode") String countryCode, @Query("stateCode") String stateCode);

    @GET("account-service/message/branches")
    Call<UBNBranchesResponse> getUBNBranches(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @GET("account-service/message/gender")
    Call<AccountProductTypeResponse> getUBNGenders(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @GET("account-service/message/status")
    Call<AccountProductTypeResponse> getMaritalStatus(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @GET("account-service/account/customer/type")
    Call<UBNCustomerTypeRequest> getCustomerTypes(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @GET("account-service/account/type")
    Call<UBNAccountTypeResponse> getUBNAccountTypes(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);


    @GET("account-service/message/all/segment")
    Call<UBNCustomerSegmentResponse> getCustomersSegment(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);


    @POST("account-service/account/initiate/account")
    Call<UBNCreateAccountNewCustomerResponse> createUBNNewCustomerAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                      @Body UBNCreateAccountNewCustomerRequest request);


    @GET("account-service/document/type")
    Call<AccountProductTypeResponse> getDocumentTypes(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                   @Query("productCode") String productCode);

    @Multipart
    @POST("account-service/document/upload")
    Call<UBNGenericResponse> uploadDocumentForAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                      @Query("recordId") Long recordId,@Query("type") Integer type,@Part() MultipartBody.Part file);

    @POST("account-service/document/submit")
    Call<UBNGenericResponse> submitDocumentsForAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                      @Query("bvn") String bvn,@Query("recordId") Long recordId);

    @POST("account-service/account/payment")
    Call<UBNAccountPaymentResponse> accountPaymentUBN1(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                     @Query("customerId") Long customerId, @Query("isReactivate") Boolean isReactivate,
                                                     @Body UBNAccountPaymentRequest request);

    @POST("account-service/account/pay")
    Call<UBNAccountPaymentResponse> accountPaymentUBN2(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                       @Query("customerId") Long customerId, @Query("oldAccNumber") String oldAccNumber,
                                                       @Body UBNAccountPaymentRequest request);

    @POST("account-service/account/confirm/payment")
    Call<UBNGenericResponse> confirmUBNPaymentStatus(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                       @Query("customerId") Long customerId);

    @POST("account-service/account/create/v2")
    Call<UBNCompleteAccountPaymentResponse> completeUBNAccountCreation(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                     @Body UBNAccountCreationRequest request);

    @GET("account-service/account")
    Call<UBNAccountDataResponse> getUBNAccountDetails(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                                 @Query("id") Long id);

    @GET("account-service/account/source")
    Call<UBNCustomerTypeRequest> getSourceOfFund(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);






}
