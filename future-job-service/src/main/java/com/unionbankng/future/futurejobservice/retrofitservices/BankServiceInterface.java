package com.unionbankng.future.futurejobservice.retrofitservices;
import com.unionbankng.future.futurejobservice.pojos.UBNBulkFundTransferRequest;
import com.unionbankng.future.futurejobservice.pojos.UBNBulkFundTransferResponse;
import com.unionbankng.future.futurejobservice.pojos.UBNFundTransferRequest;
import com.unionbankng.future.futurejobservice.pojos.UBNFundTransferResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface BankServiceInterface {

    @POST("bankserv/api/v1/ubn_funds/transfer")
    @Headers({
            "Accept: application/json"
    })
    Call<UBNFundTransferResponse> transferFunds(@Header("Authorization") String authorization, @Body UBNFundTransferRequest request);

    @POST("bankserv/api/v1/ubn_funds/bulk_transfer")
    @Headers({
            "Accept: application/json"
    })
    Call<UBNBulkFundTransferResponse> transferBulkFund(@Header("Authorization") String authorization, @Body UBNBulkFundTransferRequest request);



}