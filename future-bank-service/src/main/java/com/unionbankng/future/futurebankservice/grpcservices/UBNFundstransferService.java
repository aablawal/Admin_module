package com.unionbankng.future.futurebankservice.grpcservices;
import com.google.gson.Gson;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferRequest;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferResponse;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferServiceGrpc;
import com.unionbankng.future.futurebankservice.pojos.UBNFundTransferRequest;
import com.unionbankng.future.futurebankservice.pojos.UBNFundTransferResponse;
import com.unionbankng.future.futurebankservice.services.UBNAccountAPIServiceHandler;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Response;

import java.io.IOException;

@GrpcService
@RequiredArgsConstructor
public class UBNFundstransferService extends UBNFundsTransferServiceGrpc.UBNFundsTransferServiceImplBase {

    private final UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;
    Logger logger = LoggerFactory.getLogger(UBNFundstransferService.class);



    public void transferFund(UBNFundsTransferRequest request, StreamObserver<UBNFundsTransferResponse> responseObserver) {

        UBNFundTransferRequest transfer = new UBNFundTransferRequest();
        transfer.setAmount(Double.toString(request.getAmount()));
        transfer.setCurrency(request.getCurrency());
        transfer.setPaymentReference(request.getPaymentReference());
        transfer.setInitBranchCode(request.getInitBranchCode());
        transfer.setCreditAccountName(request.getCreditAccountName());
        transfer.setCreditAccountNumber(request.getCreditAccountNumber());
        transfer.setCreditAccountBankCode(request.getCreditAccountBankCode());
        transfer.setCreditAccountBranchCode(request.getCreditAccountBranchCode());
        transfer.setCreditAccountType(request.getCreditAccountType());
        transfer.setCreditNarration(request.getCreditNarration());
        transfer.setDebitAccountName(request.getDebitAccountName());
        transfer.setDebitAccountNumber(request.getDebitAccountNumber());
        transfer.setDebitAccountBranchCode(request.getDebitAccountBranchCode());
        transfer.setDebitAccountType(request.getDebitAccountType());
        transfer.setDebitNarration(request.getDebitNarration());

        UBNFundsTransferResponse ubnFundsTransferResponse = null;


        try {
            Response<UBNFundTransferResponse> responseResponse = ubnAccountAPIServiceHandler.transferFundsUBN(transfer);

            logger.info("Transfer response is: {}",responseResponse.code());

            if (!responseResponse.isSuccessful()) {
                ubnFundsTransferResponse = UBNFundsTransferResponse.newBuilder().setCode("01").build();

            } else {
                ubnFundsTransferResponse = UBNFundsTransferResponse.newBuilder().setCode(responseResponse.body().getCode())
                        .setReference(responseResponse.body().getReference())
                        .setMessage(responseResponse.body().getMessage()).build();
            }


        } catch (IOException e) {
            e.printStackTrace();
            ubnFundsTransferResponse = UBNFundsTransferResponse.newBuilder().setCode("01").build();
        }

        responseObserver.onNext(ubnFundsTransferResponse);
        responseObserver.onCompleted();

    }


}
