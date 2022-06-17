package com.unionbankng.future.futurebankservice.services;
import com.unionbankng.future.futurebankservice.pojos.UBNAuthServerTokenResponse;
import com.unionbankng.future.futurebankservice.retrofitservices.UBNAuthAPIServiceInterface;
import com.unionbankng.future.futurebankservice.util.App;
import com.unionbankng.future.futurebankservice.util.CryptoService;
import com.unionbankng.future.futurebankservice.util.UnsafeOkHttpClient;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RefreshScope
@Service
@RequiredArgsConstructor
public class UBNAuthService {

    private UBNAuthAPIServiceInterface ubnAuthAPIServiceInterface;
    private final App app;
    @Value("${unionbankng.base.url}")
    private String ubnBaseURL;
    @Value("${unionbankng.clientId}")
    private String clientId;
    @Value("${unionbankng.clientSecret}")
    private String clientSecret;
    @Autowired
    private CryptoService cryptoService;
    OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    @PostConstruct
    public void init() {
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ubnBaseURL)
                .build();
        ubnAuthAPIServiceInterface = retrofit.create(UBNAuthAPIServiceInterface.class);
    }

    public UBNAuthServerTokenResponse getUBNAuthServerToken() throws IOException {

        Map<String, String> credentials = new HashMap<>();

        try {
            app.print("Decrypting UBN Auth Server Token");
            String client=cryptoService.decrypt(clientId, clientSecret);
            credentials = app.getMapper().readValue(client, Map.class);
        }catch (Exception ex){
            app.print("Unable to decode cypher text to map");
            ex.printStackTrace();
        }
        app.print("Credentials Username:");
        app.print(credentials.get("username"));
        app.print(credentials.keySet());

        Call<UBNAuthServerTokenResponse> responseCall = ubnAuthAPIServiceInterface.getAuthServerToken(credentials.get("username"),credentials.get("password"),credentials.get("clientSecret"),
                credentials.get("grantType"),credentials.get("clientId"));
        UBNAuthServerTokenResponse response=  responseCall.execute().body();
        app.print("/authserv/oauth/token is :"+response.getAccess_token());
        return response;
    }

    public UBNAuthServerTokenResponse getUBNAccountServerToken() throws IOException {
        app.print("########Generating Access token:");
        Map<String, String> credentials = new HashMap<>();
        try {
            app.print("Decrypting UBN Auth Server Token");
            String client=cryptoService.decrypt(clientId, clientSecret);
            credentials = app.getMapper().readValue(client, Map.class);
        }catch (Exception ex){
            app.print("Unable to decode cypher text to map");
            ex.printStackTrace();
        }
        app.print("Credentials Username:");
        app.print(credentials.get("username"));
        app.print(credentials.keySet());

        Call<UBNAuthServerTokenResponse> responseCall = ubnAuthAPIServiceInterface.getAccountServerToken(credentials.get("username"), credentials.get("password"), credentials.get("clientSecret"),
                credentials.get("grantType"), credentials.get("clientId"));
        UBNAuthServerTokenResponse response = responseCall.execute().body();
        app.print("Response:");
        app.print(response);
        app.print("/ubnmiserv/oauth/token is :" + response.getAccess_token());
        return response;
    }
}