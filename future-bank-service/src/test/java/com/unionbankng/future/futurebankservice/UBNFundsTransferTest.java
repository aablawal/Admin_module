package com.unionbankng.future.futurebankservice;

import com.unionbankng.future.futurebankservice.pojos.*;
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
        transfer.setInitBranchCode("000");

        //credit
        transfer.setCreditAccountName("DEDICATED NEFT O A");
        transfer.setCreditAccountNumber("0055982543");
        transfer.setCreditAccountBankCode("032");
        transfer.setCreditAccountBranchCode("000");
        transfer.setCreditAccountType("CASA");
        transfer.setCreditNarration("Testing naration");

        //debit
        transfer.setDebitAccountName("OLANLOKUN LANRE A");
        transfer.setDebitAccountNumber("0040553624");
        transfer.setDebitAccountBranchCode("000");
        transfer.setDebitAccountType("CASA");
        transfer.setDebitNarration("Testing naration 2");

        Response<UBNFundTransferResponse> transferResponseResponse = ubnAccountAPIServiceHandler.transferFundsUBN(transfer);


        assertEquals(200,transferResponseResponse.code());

        logger.info(transferResponseResponse.body().toString());
        assertEquals("00",transferResponseResponse.body().getCode());

    }

    @Test
    public void accountEnquiryTest() throws IOException {

        UbnCustomerEnquiryRequest request = new UbnCustomerEnquiryRequest();
        request.setAccountNumber("0097275445");
        request.setAccountType("CASA");


        Response<UbnCustomerAccountEnquiryResponse> transferResponseResponse = ubnAccountAPIServiceHandler.accountEnquiry(request);


        assertEquals(200,transferResponseResponse.code());

        logger.info(transferResponseResponse.body().toString());
        assertEquals("00",transferResponseResponse.body().getAccount().getCode());

    }
}
