package com.unionbankng.future.futurebankservice.services;
import com.unionbankng.future.futurebankservice.pojos.UBNAuthServerTokenResponse;
import com.unionbankng.future.futurebankservice.retrofitservices.AuthServiceInterface;
import com.unionbankng.future.futurebankservice.util.App;
import com.unionbankng.future.futurebankservice.util.UnsafeOkHttpClient;
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
public class FutureAuthServiceHandler {

    private AuthServiceInterface authServiceInterface;
    private final App app;
    @Value("${kula.base-url}")
    private String kulaBaseUrl;
    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(kulaBaseUrl)
                .build();
        authServiceInterface = retrofit.create(AuthServiceInterface.class);
    }



    public void initiateKYC(String authToken, String bvn, String dob){
        try {
            app.print("Calling Future Auth Service to initiate KYC");
            authServiceInterface.initiateKYC(authToken, bvn, dob).execute();
        } catch (IOException e) {
            app.print("Unable to initiate KYC");
//            throw new RuntimeException(e);
        }
    }

}