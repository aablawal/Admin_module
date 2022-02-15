package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.CreateWalletRequest;
import com.unionbankng.future.authorizationserver.pojos.WalletAuthResponse;
import com.unionbankng.future.authorizationserver.retrofitservices.WalletServiceInterface;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WalletService implements Serializable {

    private Logger logger = LoggerFactory.getLogger(WalletService.class);
    private WalletServiceInterface walletServiceInterface;
    private final App app;

    @Value("${kula.walletBaseURL}")
    private String walletBaseURL;// = "http://localhost:9090";

    @Value("${kula.walletUsername}")
    private String username;// = "marcus";

    @Value("${kula.walletPassword}")
    private String password;// = "password";

    @PostConstruct
    public void init() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(walletBaseURL)
                .build();
        app.print(walletBaseURL);
        walletServiceInterface = retrofit.create(WalletServiceInterface.class);
    }


    private String computeBasicAuthorization(){
        String details=username+":"+password;
        app.print(details);
        return "Basic " +Base64.getEncoder().encodeToString(details.getBytes());
    }

    public WalletAuthResponse getAuth() {
        try {
            app.print("Generating Wallet access token ....");
            String  basicAuth=computeBasicAuthorization();
            app.print(basicAuth);
            Response<WalletAuthResponse> response = walletServiceInterface.getWalletServiceToken("password",username,password,basicAuth).execute();
            app.print("Response:");
            app.print(response);
            app.print(response.body());
            app.print(response.code());
            if (response.isSuccessful()) {
                return response.body();
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public APIResponse<Map<String, String>> createWallet(String userId, String customerName, String bvn) {
        try {
            app.print("Creating new Wallet....");
            app.print("userId:");
            app.print(userId);
            app.print("customerName:");
            app.print(customerName);
            app.print("bvn:");
            app.print(bvn);

            WalletAuthResponse auth= getAuth();
            if(auth!=null) {
                String token = "Bearer " + auth.getAccess_token();
                app.print("wallet auth token:");
                app.print(token);
                app.print("##### Creating new Wallet....");
                CreateWalletRequest createWalletRequest = new CreateWalletRequest(userId, customerName, bvn);
                Response<Map<String, String>> response = walletServiceInterface.createWallet(token, createWalletRequest).execute();
                app.print("Response:");
                app.print(response);
                app.print(response.body());
                app.print(response.code());
                if (response.isSuccessful()) {
                    return  new APIResponse("Request Successful",true, response.body());

                } else {
                    return  new APIResponse(response.message(),false,null);
                }
            }else{
                return  new APIResponse("Unable to generate wallet auth token",false,null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return  new APIResponse("Something went wrong",false,null);

        }
    }


}

