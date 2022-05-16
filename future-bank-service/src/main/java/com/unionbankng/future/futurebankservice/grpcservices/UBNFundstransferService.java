package com.unionbankng.future.futurebankservice.grpcservices;
import com.unionbankng.future.futurebankservice.grpc.*;
import com.unionbankng.future.futurebankservice.pojos.UBNBulkFundTransferRequest;
import com.unionbankng.future.futurebankservice.pojos.UBNBulkFundTransferResponse;
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


    @Override
    public void transferFund(UBNFundsTransferRequest request, StreamObserver<UBNFundsTransferResponse> responseObserver) {

        UBNFundTransferRequest transfer = new UBNFundTransferRequest();
        transfer.setAmount(Double.toString(request.getAmount()));
        transfer.setCurrency(request.getCurrency());
        transfer.setPaymentReference(request.getPaymentReference());
        transfer.setInitBranchCode("000");
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
                ubnFundsTransferResponse = UBNFundsTransferResponse.newBuilder()
                        .setCode(responseResponse.body().getCode()==null?"None":responseResponse.body().getCode())
                        .setReference(responseResponse.body().getReference()==null?"None":responseResponse.body().getReference())
                        .setMessage(responseResponse.body().getMessage()==null?"None":responseResponse.body().getMessage()).build();
            }


        } catch (IOException e) {
            e.printStackTrace();
            ubnFundsTransferResponse = UBNFundsTransferResponse.newBuilder().setCode("01").build();
        }

        responseObserver.onNext(ubnFundsTransferResponse);
        responseObserver.onCompleted();

    }

    @Override
    public void transferBulkFund(com.unionbankng.future.futurebankservice.grpc.UBNBulkFundsTransferRequest request,
                                 io.grpc.stub.StreamObserver<com.unionbankng.future.futurebankservice.grpc.UBNBulkFundsTransferResponse> responseObserver) {


        UBNBulkFundTransferRequest transfer = new UBNBulkFundTransferRequest();
        transfer.setCurrency(request.getCurrency());
        transfer.setPaymentReference(request.getPaymentReference());
        transfer.setInitBranchCode("000");
        transfer.setPaymentReference(request.getPaymentReference());
        transfer.setTransactionDate(request.getTransactionDate());
        transfer.setExternalSystemReference(request.getExternalSystemReference());
        for(com.unionbankng.future.futurebankservice.grpc.UBNBulkFundTransferBatchItem item: request.getBatchItemsList()){
            com.unionbankng.future.futurebankservice.pojos.UBNBulkFundTransferBatchItem itemData = new com.unionbankng.future.futurebankservice.pojos.UBNBulkFundTransferBatchItem();
            itemData.setAmount(item.getAmount());
            itemData.setAccountBankCode(item.getAccountBankCode());
            itemData.setAccountBranchCode(item.getAccountBranchCode());
            itemData.setAccountName(item.getAccountName());
            itemData.setAccountNumber(item.getAccountNumber());
            itemData.setAccountType(item.getAccountType());
            itemData.setNarration(item.getNarration());
            itemData.setCrDrFlag(item.getCrDrFlag());
            itemData.setFeeOrCharges(item.getFeeOrCharges());
            itemData.setInstrumentNumber(item.getInstrumentNumber());
            itemData.setTransactionId(item.getTransactionId());
            itemData.setValueDate(item.getValueDate());
            transfer.getBatchItems().add(itemData);
        }
        UBNBulkFundTransferResponse ubnBulkFundsTransferResponse = new UBNBulkFundTransferResponse();

        try {
            Response<UBNBulkFundTransferResponse> responseResponse = ubnAccountAPIServiceHandler.transferBulkFundsUBN(transfer);
            logger.info("Transfer response is: {}",responseResponse.code());

            if (!responseResponse.isSuccessful()) {
                ubnBulkFundsTransferResponse.setCode("01");
                ubnBulkFundsTransferResponse.setMessage(responseResponse.message());
            } else {
                ubnBulkFundsTransferResponse.setCode(responseResponse.body().getCode()==null?"None":responseResponse.body().getCode());
                ubnBulkFundsTransferResponse.setReference(responseResponse.body().getReference()==null?"None":responseResponse.body().getReference());
                ubnBulkFundsTransferResponse.setMessage(responseResponse.body().getMessage()==null?"None":responseResponse.body().getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
            ubnBulkFundsTransferResponse.setCode("01");
            ubnBulkFundsTransferResponse.setMessage(e.getMessage());
        }
        com.unionbankng.future.futurebankservice.grpc.UBNBulkFundsTransferResponse  response= UBNBulkFundsTransferResponse.newBuilder()
                .setCode(ubnBulkFundsTransferResponse.getCode()==null?"None":ubnBulkFundsTransferResponse.getCode())
                .setMessage(ubnBulkFundsTransferResponse.getMessage()==null?"None":ubnBulkFundsTransferResponse.getMessage())
                .setBatchId(ubnBulkFundsTransferResponse.getBatchId()==null?"None":ubnBulkFundsTransferResponse.getBatchId())
                .setReference(ubnBulkFundsTransferResponse.getReference()==null?"None":ubnBulkFundsTransferResponse.getReference())
                .setCbaBatchNo(ubnBulkFundsTransferResponse.getCbaBatchNo()==null?"None":ubnBulkFundsTransferResponse.getCbaBatchNo()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

}
