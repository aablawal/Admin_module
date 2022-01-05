package com.unionbankng.future.futurejobservice.retrofitservices;

import com.unionbankng.future.futurejobservice.pojos.WalletAuthResponse;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WalletServiceInterface {

    @POST("/oauth/token")
    Call<WalletAuthResponse> getWalletServiceToken(@Header("Authorization") String authorization);
}
