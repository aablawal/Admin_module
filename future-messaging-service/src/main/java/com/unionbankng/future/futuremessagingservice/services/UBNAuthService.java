package com.unionbankng.future.futuremessagingservice.services;
import com.unionbankng.future.futuremessagingservice.pojos.UBNAuthServerTokenResponse;
import com.unionbankng.future.futuremessagingservice.retrofitservices.UBNAuthAPIServiceInterface;
import com.unionbankng.future.futuremessagingservice.util.App;
import com.unionbankng.future.futuremessagingservice.util.UnsafeOkHttpClient;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@RefreshScope
@Service
@RequiredArgsConstructor
public class UBNAuthService {

    private UBNAuthAPIServiceInterface ubnAuthAPIServiceInterface;
    private final App app;
    @Value("${unionbankng.base.url}")
    private String ubnBaseURL;

    @Value("#{${unionbankng.credentials}}")
    private Map<String, String> credentials;

    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ubnBaseURL)
                .build();
        ubnAuthAPIServiceInterface = retrofit.create(UBNAuthAPIServiceInterface.class);
    }

    public UBNAuthServerTokenResponse getUBNAuthServerToken() throws IOException {
        Call<UBNAuthServerTokenResponse> responseCall = ubnAuthAPIServiceInterface.getAuthServerToken(credentials.get("username"),credentials.get("password"),credentials.get("clientSecret"),
                credentials.get("grantType"),credentials.get("clientId"));
        UBNAuthServerTokenResponse response=  responseCall.execute().body();
        app.print("/authserv/oauth/token is :"+response.getAccess_token());
        return response;
    }


}