package com.unionbankng.future.futuremessagingservice.services;

import com.unionbankng.future.futuremessagingservice.pojos.APIResponse;
import com.unionbankng.future.futuremessagingservice.pojos.EmailBody;
import com.unionbankng.future.futuremessagingservice.pojos.UbnEmailResponse;
import com.unionbankng.future.futuremessagingservice.retrofitservices.UBNEmailServiceInterface;
import com.unionbankng.future.futuremessagingservice.util.App;
import com.unionbankng.future.futuremessagingservice.util.UnsafeOkHttpClient;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.annotation.PostConstruct;
import java.util.Map;

@RefreshScope
@Service
@RequiredArgsConstructor
public class UBNEmailService {

    private UBNEmailServiceInterface ubnEmailServiceInterface;
    private final UBNAuthService ubnAuthService;
    private final App app;
    @Value("${unionbankng.base.url}")
    private String ubnBaseURL;

    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ubnBaseURL)
                .build();
        ubnEmailServiceInterface = retrofit.create(UBNEmailServiceInterface.class);
    }

    public APIResponse sendEmail(EmailBody body) {
        try {
            app.print("Sending Email...");
            String authorization = String.format("Bearer %s", ubnAuthService.getUBNAuthServerToken().getAccess_token());
            app.print(authorization);
            Response<UbnEmailResponse> responseCall = ubnEmailServiceInterface.sendEmail(authorization, body).execute();
            UbnEmailResponse response = responseCall.body();
            app.print("Response:");
            app.print(response);

            return new APIResponse(responseCall.message(), responseCall.isSuccessful(), response);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse(ex.getMessage(), false, null);
        }
    }
}
