package com.unionbankng.future.futurejobservice.services;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferRequest;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferServiceGrpc;
import com.unionbankng.future.futurejobservice.entities.JobTransfer;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferResponse;

@Service
public class BankTransferService {
    Logger logger = LoggerFactory.getLogger(BankTransferService.class);
    @GrpcClient("bankService")
    private  UBNFundsTransferServiceGrpc.UBNFundsTransferServiceBlockingStub ubnFundsTransferServiceBlockingStub;

    public UBNFundsTransferResponse transferUBNtoUBN(JobTransfer transfer){
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
        setInitBranchCode(transfer.getInitBranchCode()).
        setPaymentReference(transfer.getPaymentReference()).build();
       return ubnFundsTransferServiceBlockingStub.transferFund(request);
    }
}

