package com.unionbankng.future.learn.retrofitservices;

import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.StreamingLocatorResponse;
import com.unionbankng.future.learn.pojo.UploadMediaFromURLRequest;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.concurrent.Executor;

public interface UtilityServiceInterface {

    @Multipart
    @POST("api/v1/video_stream/upload")
    Call<APIResponse<StreamingLocatorResponse>> uploadAndGetStreamFromMultipart(@Part() MultipartBody.Part file);

    @FormUrlEncoded
    @POST("api/v1/video_stream/upload_from_url")
    Call<APIResponse<StreamingLocatorResponse>> uploadAndGetStreamFromURL(@Field("url") String url);

    @POST("api/v1/media/upload_from_url")
    Call<APIResponse<String>> uploadMediaFromURL(@Body UploadMediaFromURLRequest request);

}
