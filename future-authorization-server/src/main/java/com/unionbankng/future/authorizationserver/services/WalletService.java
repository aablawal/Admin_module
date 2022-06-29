package com.unionbankng.future.authorizationserver.services;

import com.unionbankng.future.authorizationserver.entities.User;
import com.unionbankng.future.authorizationserver.pojos.APIResponse;
import com.unionbankng.future.authorizationserver.pojos.CreateWalletRequest;
import com.unionbankng.future.authorizationserver.pojos.WalletAuthResponse;
import com.unionbankng.future.authorizationserver.repositories.UserRepository;
import com.unionbankng.future.authorizationserver.retrofitservices.WalletServiceInterface;
import com.unionbankng.future.authorizationserver.utils.App;
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
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class WalletService implements Serializable {

    private Logger logger = LoggerFactory.getLogger(WalletService.class);
    private WalletServiceInterface walletServiceInterface;
    private final UserRepository userRepository;
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
        walletServiceInterface = retrofit.create(WalletServiceInterface.class);
    }


    private String computeBasicAuthorization() {
        String details = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(details.getBytes());
    }

    public WalletAuthResponse getAuth() {
        try {
            app.print("Generating Wallet access token ....");
            String basicAuth = computeBasicAuthorization();
            Response<WalletAuthResponse> response = walletServiceInterface.getWalletServiceToken("password", username, password, basicAuth).execute();
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

    public APIResponse<Map<String, String>> createWallet(User user, String bvn, String dob) {

        if(user.getBvn() != null && !user.getBvn().equalsIgnoreCase(bvn)){
            return new APIResponse("BVN provided does not  match that associated with this account", false, null);
        }

        User testUser = userRepository.findByBvn(bvn).orElse(null);

        if(testUser != null && !testUser.equals(user)){
            return new APIResponse("BVN provided is associated to another account", false, null);
        }

        if (bvn == null || !app.validBvn(bvn))
            return new APIResponse("Provide user verified BVN Number", false, null);


        String userId = user.getUuid();
        String customerName = user.getFirstName() + " " + user.getLastName();

        try {

            user.setBvn(bvn);
            user.setKycLevel(1);
            user.setBvn(bvn);
            user.setKycLevel(1);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
            if(dob != null){
                Date dateOfBirth = formatter.parse(dob);
                user.setDateOfBirth(dateOfBirth);
            }
            userRepository.save(user);

            WalletAuthResponse auth = getAuth();

            if (auth == null)
                return new APIResponse("Sorry, we are unable to generate wallet auth token", false, null);

            String token = "Bearer " + auth.getAccess_token();
            app.print("##### Creating new Wallet....");
            CreateWalletRequest createWalletRequest = new CreateWalletRequest(userId, customerName, bvn);
            Response<Map<String, String>> response = walletServiceInterface.createWallet(token, createWalletRequest).execute();
            app.print("##### Response...." + response.body());
            if (!response.isSuccessful())
                return new APIResponse(response.message(), false, null);

            if (response.body() != null && Objects.equals(response.body().get("code"), "000")) {
                app.print("Wallet created successfully");
                user.setWalletId(response.body().get("walletId"));
                userRepository.save(user);
                return new APIResponse("BVN Added", true, user);
            } else {
                return new APIResponse<>("Sorry, Wallet creation failed at this time, try again soon!", false, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIResponse("Something went wrong", false, null);
        }
    }


}

