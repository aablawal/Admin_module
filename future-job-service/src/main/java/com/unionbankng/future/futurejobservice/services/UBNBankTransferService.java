
package com.unionbankng.future.futurejobservice.services;
import com.unionbankng.future.futurebankservice.grpc.*;
import com.unionbankng.future.futurejobservice.entities.JobPayment;
import com.unionbankng.future.futurejobservice.pojos.PaymentResponse;
import com.unionbankng.future.futurejobservice.util.App;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UBNBankTransferService {

    @GrpcClient("bankService")
    private  UBNFundsTransferServiceGrpc.UBNFundsTransferServiceBlockingStub ubnFundsTransferServiceBlockingStub;
    private final UBNTransferResponseService jobPaymentResponseService;
    private final App app;

    public PaymentResponse transferUBNtoUBN(JobPayment transfer){
        UBNFundsTransferRequest request = UBNFundsTransferRequest.newBuilder().
        setAmount(transfer.getAmount()).
        setCreditAccountName(transfer.getCreditAccountName()).
        setCreditAccountBankCode("032").
        setCreditAccountNumber(transfer.getCreditAccountNumber()).
        setCreditNarration(transfer.getDebitNarration()).
        setCreditAccountBankCode("032").
        setCreditAccountBranchCode(transfer.getCreditAccountBranchCode() == null? "000":transfer.getCreditAccountBranchCode()).
        setDebitAccountBranchCode(transfer.getDebitAccountBranchCode()== null? "000":transfer.getDebitAccountBranchCode()).
        setCreditAccountType(transfer.getCreditAccountType()).
        setCurrency(transfer.getCurrency()).
        setDebitAccountName(transfer.getDebitAccountName()).
        setDebitAccountNumber(transfer.getDebitAccountNumber()).
        setDebitNarration(transfer.getDebitNarration()).
        setDebitAccountType(transfer.getDebitAccountType()).
        setInitBranchCode(transfer.getInitBranchCode()).setChannelCode("1")
                .setValueDate("2020-12-04").setPaymentTypeCode("FT").
        setPaymentReference(transfer.getPaymentReference()).build();
        UBNFundsTransferResponse apiResponse=ubnFundsTransferServiceBlockingStub.transferFund(request);

        PaymentResponse response= new PaymentResponse();
        response.setCode(apiResponse.getCode());
        response.setMessage(jobPaymentResponseService.getResponseMessage(apiResponse.getCode(),apiResponse.getMessage()));
        response.setReference(apiResponse.getReference());

       return response;
    }


    public PaymentResponse transferBulkUBNtoUBN(UBNBulkFundsTransferRequest paymentRequest) {
        //fire the request
        UBNBulkFundsTransferResponse apiResponse=  ubnFundsTransferServiceBlockingStub.transferBulkFund(paymentRequest);
        //map the response
        PaymentResponse response= new PaymentResponse();
        response.setCode(apiResponse.getCode());
        response.setBatchId(apiResponse.getBatchId());
        response.setMessage(jobPaymentResponseService.getResponseMessage(apiResponse.getCode(),apiResponse.getMessage()));
        response.setCbaBatchNo(apiResponse.getCbaBatchNo());
        response.setReference(apiResponse.getReference());

        return  response;
    }
}

