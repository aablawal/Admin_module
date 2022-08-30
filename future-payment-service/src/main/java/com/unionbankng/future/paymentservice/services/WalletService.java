package com.unionbankng.future.paymentservice.services;

import com.unionbankng.future.paymentservice.pojos.*;
import com.unionbankng.future.paymentservice.retrofitservices.WalletServiceInterface;
import com.unionbankng.future.paymentservice.utils.App;
import com.unionbankng.future.paymentservice.utils.JWTUserDetailsExtractor;
import com.unionbankng.future.paymentservice.utils.PaystackApiHandler;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import javax.net.ssl.*;
import java.io.Serializable;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class WalletService implements Serializable {

    private Logger logger = LoggerFactory.getLogger(WalletService.class);
    private WalletServiceInterface walletServiceInterface;

    @Autowired
    private PaystackApiHandler paystackApiHandler;
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

        Retrofit retrofit = new Retrofit.Builder().client(getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(walletBaseURL)
                .build();
        walletServiceInterface = retrofit.create(WalletServiceInterface.class);
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            });
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String computeBasicAuthorization(){
        String details=username+":"+password;
        return "Basic " +Base64.getEncoder().encodeToString(details.getBytes());
    }

    public WalletAuthResponse getAuth() {
        try {
            app.print("Generating Wallet access token ....");
            String  basicAuth=computeBasicAuthorization();
            Response<WalletAuthResponse> response = walletServiceInterface.getWalletServiceToken("password",username,password,basicAuth).execute();
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


    public ApiResponse<WalletGenericResponse> creditWallet(WalletCreditRequest request) {
        try {
            app.print("Crediting Wallet....");
            app.print("Request:");
            app.print(request);

            WalletAuthResponse auth= getAuth();
            if(auth!=null) {
                String token = "Bearer " + auth.getAccess_token();
                Response<WalletGenericResponse> response = walletServiceInterface.creditWallet(token,request).execute();
                app.print("Response:");
                app.print(response);
                app.print(response.body());
                app.print(response.code());
                if (response.isSuccessful()) {
                    return  new ApiResponse("Request Successful",true, response.body());

                } else {
                    return  new ApiResponse(response.message(),false,null);
                }
            }else{
                return  new ApiResponse("Unable to generate wallet auth token",false,null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return  new ApiResponse("Something went wrong",false,null);

        }
    }

    public WalletGenericResponse debitWallet(WalletDebitRequest request) {
        try {
            app.print("Crediting Wallet....");
            app.print("Request:");
            app.print(request);
            WalletAuthResponse auth= getAuth();
            if(auth!=null) {
                String token = "Bearer " + auth.getAccess_token();
                Response<WalletGenericResponse> response = walletServiceInterface.debitWallet(token,request).execute();
                app.print("Response:");
                app.print(response.code());
                if (response.isSuccessful()) {
                    return  response.body();
                } else {
                    return  new WalletGenericResponse(response.message(), "101",  null, false);
                }
            }else{
                return  new WalletGenericResponse("Wallet authentication failed", "101",  null, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return  new WalletGenericResponse("Something went wrong","101", null, false);

        }
    }

    public ApiResponse<WalletGenericResponse> verifyTransaction(InterswitchSDKResponse interswitchSDKResponse) {
        try {
            app.print("Verifying transaction....");
            WalletAuthResponse auth= getAuth();
            if(auth!=null) {
                String token = "Bearer " + auth.getAccess_token();
                Response<ApiResponse<WalletGenericResponse>> response = walletServiceInterface.verifyInterswitchTransaction(token, interswitchSDKResponse.getTxnref(), interswitchSDKResponse).execute();
                app.print("Response:");
                app.print(response.body());
                app.print(response.code());
                if (response.isSuccessful()) {
                    app.print("verifyTransaction successful");
                    app.print(response.body());
                    return response.body();

                } else {
                    return  new ApiResponse<>(response.message(),false,null);
                }
            }else{
                return  new ApiResponse<>("Unable to authenticate with wallet service",false,null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return  new ApiResponse<>("Something went wrong while verifying transaction",false,null);
        }
    }

    public ApiResponse<WalletGenericResponse> verifyTransaction(PaystackSDKResponse request, OAuth2Authentication details) {
        JwtUserDetail authorizedUser = JWTUserDetailsExtractor.getUserDetailsFromAuthentication(details);

        if(Objects.isNull(authorizedUser)){
            return  new ApiResponse<>("User details not found",false,null);
        }

        app.print("authorizedUser...."+authorizedUser);
        request.setWalletId(request.getWalletId() == null ? authorizedUser.getWalletId() : request.getWalletId() );
        request.setCustomerEmail(request.getCustomerEmail() == null ? authorizedUser.getUserEmail() : request.getCustomerEmail() );
        request.setCustomerName(request.getCustomerName() == null ? authorizedUser.getUserFullName() : request.getCustomerName() );

        app.print("request...."+request);
        try {
            app.print("Verifying transaction Response....");
            WalletAuthResponse auth= getAuth();
            if(auth!=null) {
                String token = "Bearer " + auth.getAccess_token();
                app.print("wallet service token...." + token);
                Response<ApiResponse<WalletGenericResponse>> response = walletServiceInterface.verifyPaystackTransaction(token, request).execute();
                app.print("Response:");
                app.print(response.body());
                app.print(response.code());
                if (response.isSuccessful()) {
                    app.print("verifyTransaction successful");
                    app.print(response.body());
                    return response.body();

                } else {
                    return  new ApiResponse<>(response.message(),false,null);
                }
            }else{
                return  new ApiResponse<>("Unable to authenticate with wallet service",false,null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return  new ApiResponse<>("Something went wrong while verifying transaction",false,null);
        }
    }


    public ApiResponse<InterswitchAccountValidationResponse> validateBankAccount(String accountNo, String bankCode) {
        try {
            app.print("Validating Bank account....");
            WalletAuthResponse auth= getAuth();
            if(auth!=null) {
                String token = "Bearer " + auth.getAccess_token();
                Response<ApiResponse<InterswitchAccountValidationResponse>> response = walletServiceInterface.validateBankAccount(token, accountNo, bankCode).execute();
                app.print("Response:");
                app.print(response.code());
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    return  new ApiResponse<>(response.message(),false,null);
                }
            }else{
                return  new ApiResponse<>("Unable to authenticate with wallet service",false,null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return  new ApiResponse<>("Something went wrong while verifying transaction",false,null);
        }
    }

    public WalletGenericResponse getWalletBalance(String walletId, String currencyCode) {
        try {
            app.print("Fetching wallet balance....");
            WalletAuthResponse auth = getAuth();
            if(auth!=null) {
                String token = "Bearer " + auth.getAccess_token();
                Response<WalletGenericResponse> response = walletServiceInterface.getWalletBalance(token, walletId, currencyCode).execute();
                app.print("Response:");
                app.print(response.code());
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    return  new WalletGenericResponse(response.message(), "101", null, false);
                }
            }else{
                return  new WalletGenericResponse("Unable to authenticate with wallet service","101", null, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return  new WalletGenericResponse("Something went wrong","101", null, false);
        }
    }


    public ApiResponse<List<XLog>> fetchLogsByWalletId(String walletId) {
        try {
            app.print("Fetching logs....");
            WalletAuthResponse auth = getAuth();
            if(auth!=null) {
                String token = "Bearer " + auth.getAccess_token();
                Response<ApiResponse<List<XLog>>> response = walletServiceInterface.fetchLogsByWalletId(token, walletId).execute();
                app.print("Response:");
                app.print(response.code());
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    return new ApiResponse<>(response.message(), false, "101", null);
                }
            }else{
                return new ApiResponse<>("Unable to authenticate with wallet service",false, "101", null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>("Something went wrong",false, "101", null);
        }
    }


    public ApiResponse<InterswitchBankResponse> getBankList() {
        try {
            app.print("Fetching interswitch bank list....");
            WalletAuthResponse auth = getAuth();
            if(auth!=null) {
                String token = "Bearer " + auth.getAccess_token();
                Response<ApiResponse<InterswitchBankResponse>> response = walletServiceInterface.getBankInterswitchList(token).execute();
                app.print("Response:");
                app.print(response.code());
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    return new ApiResponse<>(response.message(), false, "101", null);
                }
            }else{
                return new ApiResponse<>("Unable to authenticate with wallet service",false, "101", null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>("Something went wrong",false, "101", null);
        }
    }

    public ApiResponse<?> initiateWalletFunding(InitiateFundingRequest request) {
        try {
            app.print("Initiating wallet funding....");
            WalletAuthResponse auth = getAuth();
            if(auth!=null) {
                String token = "Bearer " + auth.getAccess_token();
                Response<ApiResponse> response = walletServiceInterface.initiateWalletFunding(token, request).execute();
                app.print("Response:");
                app.print(response.code());
                if (response.isSuccessful()) {
                    return response.body();
                } else {
                    return new ApiResponse<>(response.message(), false, "101", null);
                }
            }else{
                return new ApiResponse<>("Unable to authenticate with wallet service",false, "101", null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ApiResponse<>("Something went wrong",false, "101", null);
        }
    }

}