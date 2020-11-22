package com.unionbankng.future.learn.services;

import com.unionbankng.future.learn.pojo.APIResponse;
import com.unionbankng.future.learn.pojo.StreamingLocatorResponse;
import com.unionbankng.future.learn.retrofitservices.UtilityServiceInterface;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UtilityServiceInterfaceService {


    @Value("${sidekiq.utility-service.url}")
    private String utilityServiceBaseURL;

    private UtilityServiceInterface utilityServiceInterface;


    @PostConstruct
    public void init() {

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(utilityServiceBaseURL)
                .build();

        utilityServiceInterface= retrofit.create(UtilityServiceInterface.class);

    }

    public Response<APIResponse<StreamingLocatorResponse>> uploadVideoStream(String token, MultipartFile file) throws IOException {

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.get(file.getContentType()),
                        file.getBytes()
                );

        String authorization = String.format("Bearer %s",token);
        return utilityServiceInterface.upload(
                authorization,requestFile).execute();

    }
}
