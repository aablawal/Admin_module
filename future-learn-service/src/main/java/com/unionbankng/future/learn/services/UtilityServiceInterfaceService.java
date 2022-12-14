package com.unionbankng.future.learn.services;

import com.unionbankng.future.futureutilityservice.grpcserver.BlobType;
import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.StreamingLocatorResponse;
import com.unionbankng.future.learn.pojo.UploadMediaFromURLRequest;
import com.unionbankng.future.learn.retrofitservices.UtilityServiceInterface;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UtilityServiceInterfaceService {


    @Value("${sidekiq.utility-service.url}")
    private String utilityServiceBaseURL;

    private UtilityServiceInterface utilityServiceInterface;


    @PostConstruct
    public void init() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.MINUTES)
                .writeTimeout(60, TimeUnit.MINUTES).build();

        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(utilityServiceBaseURL)
                .build();

        utilityServiceInterface= retrofit.create(UtilityServiceInterface.class);

    }

    public Response<APIResponse<StreamingLocatorResponse>> uploadVideoStream(String token, MultipartFile file) throws IOException {

        RequestBody requestFile =
                RequestBody.create(MediaType.get(file.getContentType()),
                        file.getBytes()
                );

        MultipartBody.Part fileData =
                MultipartBody.Part.createFormData("file", "fileName.mp4", requestFile);

        return utilityServiceInterface.uploadAndGetStreamFromMultipart(
                fileData).execute();

    }
    public Response<APIResponse<StreamingLocatorResponse>> uploadVideoStreamFromURL(String url) throws IOException {
        return utilityServiceInterface.uploadAndGetStreamFromURL(url).execute();
    }

    public Response<APIResponse<String>> uploadMediaFromURL(UploadMediaFromURLRequest request) throws IOException {
        return utilityServiceInterface.uploadMediaFromURL(request).execute();
    }
}
