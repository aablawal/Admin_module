
package com.unionbankng.future.futurejobservice.services;
import com.unionbankng.future.futurejobservice.pojos.*;
import com.unionbankng.future.futurejobservice.entities.JobPayment;
import com.unionbankng.future.futurejobservice.retrofitservices.BankServiceInterface;
import com.unionbankng.future.futurejobservice.util.App;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UBNBankTransferService {

    private final UBNTransferResponseService jobPaymentResponseService;
    private BankServiceInterface bankServiceInterface;
    private final App app;

    @Value("${kula.bankService.baseURL}")
    private String bankServiceBaseURL;


    @PostConstruct
    public void init() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .baseUrl(bankServiceBaseURL)
                .build();
        app.print(bankServiceBaseURL);
        bankServiceInterface = retrofit.create(BankServiceInterface.class);
    }


    public PaymentResponse transferUBNtoUBN(JobPayment transfer) throws IOException {
        app.print("Transfer Request >>>");
        app.print(transfer);
        UBNFundTransferRequest request = new UBNFundTransferRequest();
        request.setAmount(String.valueOf(transfer.getAmount()));
        request.setCreditAccountName(transfer.getCreditAccountName());
        request.setCreditAccountBankCode("032");
        request.setCreditAccountNumber(transfer.getCreditAccountNumber());
        request.setCreditNarration(transfer.getDebitNarration());
        request.setCreditAccountBankCode("032");
        request.setCreditAccountBranchCode(transfer.getCreditAccountBranchCode() == null ? "000" : transfer.getCreditAccountBranchCode());
        request.setDebitAccountBranchCode(transfer.getDebitAccountBranchCode() == null ? "000" : transfer.getDebitAccountBranchCode());
        request.setCreditAccountType(transfer.getCreditAccountType());
        request.setCurrency(transfer.getCurrency());
        request.setDebitAccountName(transfer.getDebitAccountName());
        request.setDebitAccountNumber(transfer.getDebitAccountNumber());
        request.setDebitNarration(transfer.getDebitNarration());
        request.setDebitAccountType(transfer.getDebitAccountType());
        request.setInitBranchCode(transfer.getInitBranchCode());
        request.setChannelCode("1");
        request.setValueDate("2020-12-04");
        request.setPaymentTypeCode("FT");
        request.setPaymentReference(transfer.getPaymentReference());
        String token = "";
        Response<UBNFundTransferResponse> apiResponse = bankServiceInterface.transferFunds(token, request).execute();
        PaymentResponse response = new PaymentResponse();
        response.setCode(apiResponse.body().getCode());
        response.setMessage(jobPaymentResponseService.getResponseMessage(apiResponse.body().getCode(), apiResponse.body().getMessage()));
        response.setReference(apiResponse.body().getReference());

        return response;

    }

    public PaymentResponse transferBulkUBNtoUBN(UBNBulkFundTransferRequest paymentRequest) throws IOException {
        app.print("Bulk Transfer Request >>>");
         //fire the request
        String token = "";
        Response<UBNBulkFundTransferResponse> apiResponse = bankServiceInterface.transferBulkFund(token, paymentRequest).execute();
        PaymentResponse response = new PaymentResponse();
        response.setCode(apiResponse.body().getCode());
        response.setBatchId(apiResponse.body().getBatchId());
        response.setMessage(jobPaymentResponseService.getResponseMessage(apiResponse.body().getCode(), apiResponse.body().getMessage()));
        response.setCbaBatchNo(apiResponse.body().getCbaBatchNo());
        response.setReference(apiResponse.body().getReference());
        return response;
    }
}

