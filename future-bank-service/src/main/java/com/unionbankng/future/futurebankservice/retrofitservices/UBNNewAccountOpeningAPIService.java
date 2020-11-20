package com.unionbankng.future.futurebankservice.retrofitservices;

import com.unionbankng.future.futurebankservice.pojos.*;
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



}
