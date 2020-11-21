package com.unionbankng.future.futurebankservice.grpcservices;

import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferRequest;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferResponse;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferServiceGrpc;
import com.unionbankng.future.futurebankservice.pojos.UBNFundTransferRequest;
import com.unionbankng.future.futurebankservice.pojos.UBNFundTransferResponse;
import com.unionbankng.future.futurebankservice.services.UBNAccountAPIServiceHandler;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import retrofit2.Response;

import java.io.IOException;

@GrpcService
@RequiredArgsConstructor
public class UBNFundstransferService extends UBNFundsTransferServiceGrpc.UBNFundsTransferServiceImplBase {

    private final UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;


    public void transferFund(UBNFundsTransferRequest request, StreamObserver<UBNFundsTransferResponse> responseObserver) {

        UBNFundTransferRequest ubnFundTransferRequest = new UBNFundTransferRequest();
        ubnFundTransferRequest.setAmount(Double.toString(request.getAmount()));
        ubnFundTransferRequest.setCreditAccountBankCode(request.getCreditAccountBankCode());
        ubnFundTransferRequest.setCreditAccountBranchCode(request.getCreditAccountBranchCode());
        ubnFundTransferRequest.setCreditAccountName(request.getCreditAccountName());
        ubnFundTransferRequest.setCreditAccountNumber(request.getCreditAccountNumber());
        ubnFundTransferRequest.setCreditAccountType(request.getCreditAccountType());
        ubnFundTransferRequest.setCreditNarration(request.getCreditNarration());
        ubnFundTransferRequest.setCurrency(request.getCurrency());
        ubnFundTransferRequest.setDebitAccountBranchCode(request.getDebitAccountBranchCode());
        ubnFundTransferRequest.setDebitAccountName(request.getDebitAccountName());
        ubnFundTransferRequest.setDebitAccountNumber(request.getDebitAccountNumber());
        ubnFundTransferRequest.setDebitAccountType(request.getDebitAccountType());
        ubnFundTransferRequest.setDebitInstrumentNumber(request.getDebitInstrumentNumber());
        ubnFundTransferRequest.setDebitNarration(request.getDebitNarration());
        ubnFundTransferRequest.setFeeEntryMode(request.getFeeEntryMode());
        ubnFundTransferRequest.setInitBranchCode(request.getInitBranchCode());
        ubnFundTransferRequest.setPaymentReference(request.getPaymentReference());
        ubnFundTransferRequest.setPaymentTypeCode(request.getPaymentTypeCode());
        ubnFundTransferRequest.setTEST22(request.getTEST22());
        ubnFundTransferRequest.setValueDate(request.getValueDate());
        ubnFundTransferRequest.setChannelCode(request.getChannelCode());
        ubnFundTransferRequest.setBeneficiaryBvn(request.getBeneficiaryBvn());
        ubnFundTransferRequest.setBeneficiaryKycLevel(request.getBeneficiaryKycLevel());
        ubnFundTransferRequest.setSenderBvn(request.getSenderBvn());
        ubnFundTransferRequest.setSenderKycLevel(request.getSenderKycLevel());
        ubnFundTransferRequest.setNameEnquirySessionId(request.getNameEnquirySessionId());
        ubnFundTransferRequest.setFtSessionId(request.getFtSessionId());
        ubnFundTransferRequest.setTransactionLocation(request.getTransactionLocation());


        UBNFundsTransferResponse ubnFundsTransferResponse = null;

        try {
            Response<UBNFundTransferResponse> responseResponse = ubnAccountAPIServiceHandler.transferFundsUBN(ubnFundTransferRequest);
            if (!responseResponse.isSuccessful()) {
                ubnFundsTransferResponse = UBNFundsTransferResponse.newBuilder().setCode("01").build();

            } else {
                ubnFundsTransferResponse = UBNFundsTransferResponse.newBuilder().setCode(responseResponse.body().getCode())
                        .setReference(responseResponse.body().getReference()).setSessionId(responseResponse.body().getSessionId())
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
