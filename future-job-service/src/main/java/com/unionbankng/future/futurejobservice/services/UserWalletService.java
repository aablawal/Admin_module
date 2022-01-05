package com.unionbankng.future.futurejobservice.services;

import com.unionbankng.future.futurebankservice.grpc.UBNBulkFundTransferBatchItem;
import com.unionbankng.future.futurebankservice.grpc.UBNBulkFundsTransferRequest;
import com.unionbankng.future.futurejobservice.entities.JobBulkPayment;
import com.unionbankng.future.futurejobservice.entities.JobPayment;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.pojos.PaymentRequest;
import com.unionbankng.future.futurejobservice.pojos.PaymentResponse;
import com.unionbankng.future.futurejobservice.pojos.WalletAuthResponse;
import com.unionbankng.future.futurejobservice.repositories.JobBulkPaymentRepository;
import com.unionbankng.future.futurejobservice.repositories.JobPaymentRepository;
import com.unionbankng.future.futurejobservice.retrofitservices.WalletServiceInterface;
import com.unionbankng.future.futurejobservice.util.App;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserWalletService implements Serializable {

    private Logger logger = LoggerFactory.getLogger(UserWalletService.class);
    private WalletServiceInterface walletServiceInterface;
    private final App app;

    @Value("${kula.walletBaseURL}")
    private String walletBaseURL;

    @Value("${kula.walletUsername}")
    private String username;

    @Value("${kula.walletPassword}")
    private String password;

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
        return "Basic " +Base64.getEncoder().encodeToString(details.getBytes());
    }

    public WalletAuthResponse getAuth() {
        try {
            app.print("Generating Wallet access token ....");
            String  basicAuth=computeBasicAuthorization();
            Response<WalletAuthResponse> response = walletServiceInterface.getWalletServiceToken(basicAuth).execute();
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



}

