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
    @GET("account-opening/account/id-card/type")
    Call<AccountIdTypesResponse> getSupportedIdCardTypes(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @POST("account-opening/account/product")
    Call<AccountProductTypeResponse> getProductTypes(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                 @Body AccountProductTypeRequest request);

    @GET("account-opening/account/product/details")
    Call<ProductDetailsResponse> getProductDetails(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                     @Query("productCode") String productCode);

    @GET("account-opening/message/country")
    Call<UBNCountryResponse> getCountriesForAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @GET("account-opening/message/country")
    Call<UBNStateByCountryResponse> getStatesByCountryForAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                          @Query("countryCode") String countryCode);

    @GET("account-opening/message/country")
    Call<UBNCitiesResponse> getCitiesByCountryAndStateForAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                                 @Query("countryCode") String countryCode, @Query("stateCode") String stateCode);

    @GET("account-opening/message/branches")
    Call<UBNBranchesResponse> getUBNBranches(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @GET("account-opening/message/gender")
    Call<AccountProductTypeResponse> getUBNGenders(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @GET("account-opening/message/status")
    Call<AccountProductTypeResponse> getMaritalStatus(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @GET("account-opening/account/customer/type")
    Call<UBNCustomerTypeRequest> getCustomerTypes(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @GET("account-opening/account/type")
    Call<UBNAccountTypeResponse> getUBNAccountTypes(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);


    @GET("account-opening/message/all/segment")
    Call<UBNCustomerSegmentResponse> getCustomersSegment(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);


    @POST("account-opening/account/initiate/account")
    Call<UBNCreateAccountNewCustomerResponse> createUBNNewCustomerAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                      @Body UBNCreateAccountNewCustomerRequest request);


    @GET("account-opening/document/type")
    Call<AccountProductTypeResponse> getDocumentTypes(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                   @Query("productCode") String productCode);

    @Multipart
    @POST("account-opening/document/upload")
    Call<UBNGenericResponse> uploadDocumentForAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                      @Query("recordId") Long recordId,@Query("type") Integer type,@Part() MultipartBody.Part file);

    @POST("account-opening/document/submit")
    Call<UBNGenericResponse> submitDocumentsForAccount(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                      @Query("bvn") String bvn,@Query("recordId") Long recordId);

    @POST("account-opening/account/payment")
    Call<UBNAccountPaymentResponse> accountPaymentUBN1(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                     @Query("customerId") Long customerId, @Query("isReactivate") Boolean isReactivate,
                                                     @Body UBNAccountPaymentRequest request);

    @POST("account-opening/account/pay")
    Call<UBNAccountPaymentResponse> accountPaymentUBN2(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                       @Query("customerId") Long customerId, @Query("oldAccNumber") String oldAccNumber,
                                                       @Body UBNAccountPaymentRequest request);

    @POST("account-opening/account/confirm/payment")
    Call<UBNGenericResponse> confirmUBNPaymentStatus(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                       @Query("customerId") Long customerId);

    @POST("account-opening/account/add/account")
    Call<UBNCompleteAccountPaymentResponse> completeUBNAccountCreation(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                     @Body CompleteUBNAccountCreationRequest request);






}
