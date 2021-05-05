package com.unionbankng.future.futureloanservice;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferResponse;
import com.unionbankng.future.futureloanservice.entities.JobTransfer;
import com.unionbankng.future.futureloanservice.services.BankTransferService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

class BankTransferServiceTest {

    @Autowired
    BankTransferService bankTransferService;

    @Test
    void transferUBNtoUBN() {

        JobTransfer transfer=new JobTransfer();

        transfer.setUserId(Long.valueOf(1));
        transfer.setJobId(Long.valueOf(1));
        transfer.setProposalId(Long.valueOf(1));
        transfer.setEmployerId(Long.valueOf(1));
        transfer.setCreatedAt(new Date());

        //transfer
        transfer.setAmount(1);
        transfer.setCurrency("NGN");
        transfer.setPaymentReference("j2hyewd798hsoqg2t8179qw8o");
        transfer.setInitBranchCode("682");

        //credit
        transfer.setCreditAccountName("DEDICATED NEFT O A");
        transfer.setCreditAccountNumber("0055982543");
        transfer.setCreditAccountBankCode("032");
        transfer.setCreditAccountBranchCode("682");
        transfer.setCreditAccountType("CASA");
        transfer.setCreditNarration("New Narration");

        //debit
        transfer.setDebitAccountName("OLANLOKUN LANRE A");
        transfer.setDebitAccountNumber("0040553624");
        transfer.setDebitAccountBranchCode("682");
        transfer.setDebitAccountType("CASA");
        transfer.setDebitNarration("New Naration");
        UBNFundsTransferResponse response= bankTransferService.transferUBNtoUBN(transfer);

        Assert.assertEquals("00",response.getCode());
    }
}