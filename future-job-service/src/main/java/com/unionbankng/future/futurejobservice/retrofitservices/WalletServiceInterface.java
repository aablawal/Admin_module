package com.unionbankng.future.futurejobservice.retrofitservices;

import com.unionbankng.future.futurejobservice.pojos.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface WalletServiceInterface {

    @POST("oauth/token")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json"
    })
    Call<WalletAuthResponse> getWalletServiceToken(@Field("grant_type") String grantType,
                                                   @Field("username") String username,
                                                   @Field("password") String password,
                                                   @Header("Authorization") String authorization);

    @POST("api/v1/wallet/instant_credit")
    @Headers({
            "Accept: application/json"
    })
    Call<WalletDebitCreditResponse> creditWallet(@Header("Authorization") String authorization, @Body WalletCreditRequest request);


    @POST("api/v1/wallet/instant_debit")
    @Headers({
            "Accept: application/json"
    })
    Call<WalletDebitCreditResponse> debitWallet(@Header("Authorization") String authorization, @Body WalletDebitRequest request);



    @POST("/api/v1/wallet/outflow")
    @Headers({
            "Accept: application/json"
    })
    Call<WalletDebitCreditResponse> outflow(@Header("Authorization") String authorization, @Body WalletDebitRequest request);


    @POST("/api/v1/wallet/bulk-outflow")
    @Headers({
            "Accept: application/json"
    })
    Call<WalletDebitCreditResponse> bulkOutflow(@Header("Authorization") String authorization, @Body WalletBulkDebitRequest request);


}
