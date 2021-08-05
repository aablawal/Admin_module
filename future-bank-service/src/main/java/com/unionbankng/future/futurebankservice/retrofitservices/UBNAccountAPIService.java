package com.unionbankng.future.futurebankservice.retrofitservices;

import com.unionbankng.future.futurebankservice.pojos.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface UBNAccountAPIService {

    @POST("authserv/oauth/token")
    Call<UBNAuthServerTokenResponse> getAuthServerToken(@Query("username") String username, @Query("password") String password,
                                                        @Query("client_secret") String clientSecret, @Query("grant_type") String grantType,
                                                        @Query("client_id") String clientId);
    @POST("ubnmiserv/oauth/token")
    Call<UBNAuthServerTokenResponse> getAccountServerToken(@Query("username") String username,@Query("password") String password,
                                                  @Query("client_secret") String clientSecret,@Query("grant_type") String grantType,
                                                  @Query("client_id") String clientId);

    @POST("account-service/account/validate/bvn")
    Call<BVNValidationResponse> validateBVN(@Header("Authorization") String token, @Header("ChannelCode") String channelCode, @Body ValidateBvnRequest request);

    @POST("account-service/account/validate/otp")
    Call<BVNVerificationResponse> verifyBVN(@Header("Authorization") String token, @Header("ChannelCode") String channelCode, @Body VerifyBvnRequest request);


    @POST("ubnmiserv/secured/openaccount")
    Call<UBNCreateAccountResponse> openAccount(@Query("access_token") String accessToken, @Body UBNCreateAccountNewCustomerRequest request);

    @POST("ubnmiserv/secured/addaccounttocustomer")
    Call<UBNCreateAccountResponse> openAccountExistingCustomer(@Query("access_token") String accessToken, @Body UBNOpenAccountExistingCustomerRequest request);

    @GET("account/id-card/type")
    Call<AccountIdTypesResponse> getSupportedIdCardTypes(@Header("Authorization") String token, @Header("ChannelCode") String channelCode);

    @POST("ubnmiserv/secured/fundstransfer")
    Call<UBNFundTransferResponse> fundsTransferUBN(@Query("access_token") String accessToken, @Body UBNFundTransferRequest request);

    @POST("ubnmiserv/secured/cbamultipleposting")
    Call<UBNBulkFundTransferResponse> bulkFundsTransferUBN(@Query("access_token") String accessToken, @Body UBNBulkFundTransferRequest request);

    @POST("ubnmiserv/secured/cbacustomeraccountenquiry")
    Call<UbnCustomerAccountEnquiryResponse> accountEnquiry(@Query("access_token") String accessToken, @Body UbnCustomerEnquiryRequest request);


    @POST("ubnmiserv/secured/accountNumber")
    Call<UBNAccountStatementResponse> accountStatement(@Query("access_token") String accessToken, @Body UBNAccountStatementRequest request);


    @GET("ubnmiserv/secured/getaccountsbymobilenumber")
    Call<UBNGetAccountsResponse> getAccountsByMobileNumber(@Header("Authorization") String token, @Header("ChannelCode") String channelCode,
                                                           @Body UBNGetAccountsRequest request);
}
