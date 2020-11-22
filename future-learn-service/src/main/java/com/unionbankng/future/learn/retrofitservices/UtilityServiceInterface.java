package com.unionbankng.future.learn.retrofitservices;

import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.StreamingLocatorResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface UtilityServiceInterface {



    @Multipart
    @POST("api/v1/video_stream/upload")
    Call<APIResponse<StreamingLocatorResponse>> upload(@Header("Authorization") String token, @Part("file") RequestBody file);


}
