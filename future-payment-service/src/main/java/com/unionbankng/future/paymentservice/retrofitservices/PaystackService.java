package com.unionbankng.future.paymentservice.retrofitservices;

import com.unionbankng.future.paymentservice.pojos.PaystackVerifyTransactionResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PaystackService {

    @GET("transaction/verify/{reference}")
    Call<PaystackVerifyTransactionResponse> verifyTransaction(@Path("reference") String reference);
}
