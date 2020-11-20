package com.unionbankng.future.futurebankservice.retrofitservices;

import com.unionbankng.future.futurebankservice.pojos.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UBNAccountAPIService {

    @POST("authserv/oauth/token")
    Call<UBNAuthServerTokenResponse> getAuthServerToken(@Query("username") String username, @Query("password") String password,
                                                        @Query("client_secret") String clientSecret, @Query("grant_type") String grantType,
                                                        @Query("client_id") String clientId);
    @POST("ubnmiserv/oauth/token")
    Call<UBNAuthServerTokenResponse> getAccountServerToken(@Query("username") String username,@Query("password") String password,
                                                  @Query("client_secret") String clientSecret,@Query("grant_type") String grantType,
                                                  @Query("client_id") String clientId);

    @POST("BVNValidationService2/verifyBVN")
    Call<ValidateBvnResponse> validateBVN(@Query("access_token") String accessToken, @Body ValidateBvnRequest request);

    @POST("ubnmiserv/secured/openaccount")
    Call<UBNCreateAccountResponse> openAccount(@Query("access_token") String accessToken, @Body UBNCreateAccountNewCustomerRequest request);

    @POST("ubnmiserv/secured/addaccounttocustomer")
    Call<UBNCreateAccountResponse> openAccountExistingCustomer(@Query("access_token") String accessToken, @Body UBNOpenAccountExistingCustomerRequest request);

    @GET("account/id-card/type")
    Call<AccountIdTypesResponse> getSupportedIdCardTypes();

    @POST("ubnmiserv/secured/fundstransfer")
    Call<UBNFundTransferResponse> fundsTransferUBN(@Query("access_token") String accessToken, @Body UBNFundTransferRequest request);




}
