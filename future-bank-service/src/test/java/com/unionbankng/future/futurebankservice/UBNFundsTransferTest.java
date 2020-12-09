package com.unionbankng.future.futurebankservice;

import com.unionbankng.future.futurebankservice.pojos.UBNFundTransferRequest;
import com.unionbankng.future.futurebankservice.pojos.UBNFundTransferResponse;
import com.unionbankng.future.futurebankservice.services.UBNAccountAPIServiceHandler;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UBNFundsTransferTest extends AbstractTest {

    @Autowired
    UBNAccountAPIServiceHandler ubnAccountAPIServiceHandler;

    Logger logger = LoggerFactory.getLogger(UBNFundsTransferTest.class);

    @Test
    public void fundsTransferTest() throws IOException {

        UBNFundTransferRequest transfer = new UBNFundTransferRequest();
        //transfer
        transfer.setAmount("20");
        transfer.setCurrency("NGN");
        transfer.setPaymentReference(System.currentTimeMillis()+"");
        transfer.setInitBranchCode("682");

        //credit
        transfer.setCreditAccountName("DEDICATED NEFT O A");
        transfer.setCreditAccountNumber("0055982543");
        transfer.setCreditAccountBankCode("032");
        transfer.setCreditAccountBranchCode("682");
        transfer.setCreditAccountType("CASA");
        transfer.setCreditNarration("Testing naration");

        //debit
        transfer.setDebitAccountName("OLANLOKUN LANRE A");
        transfer.setDebitAccountNumber("0040553624");
        transfer.setDebitAccountBranchCode("682");
        transfer.setDebitAccountType("CASA");
        transfer.setDebitNarration("Testing naration 2");

        Response<UBNFundTransferResponse> transferResponseResponse = ubnAccountAPIServiceHandler.transferFundsUBN(transfer);


        assertEquals(200,transferResponseResponse.code());

        logger.info(transferResponseResponse.body().toString());
        assertEquals("00",transferResponseResponse.body().getCode());

    }
}
