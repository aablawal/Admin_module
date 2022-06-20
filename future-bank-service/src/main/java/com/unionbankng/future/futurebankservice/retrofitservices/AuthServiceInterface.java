package com.unionbankng.future.futurebankservice.retrofitservices;
import com.unionbankng.future.futurebankservice.pojos.UBNAuthServerTokenResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface AuthServiceInterface {

    @POST("authserv/api/v1/kyc/initiation")
    @Headers({
            "Accept: application/json"
    })
    Call<Map<String, String>> initiateKYC(@Header("Authorization") String authorization, @Query("bvn") String bvn, @Query("dob") String dob);


}