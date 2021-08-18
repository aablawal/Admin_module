package com.unionbankng.future.futurejobservice;
import com.unionbankng.future.futurejobservice.entities.JobPayment;
import com.unionbankng.future.futurejobservice.pojos.PaymentResponse;
import com.unionbankng.future.futurejobservice.services.UBNBankTransferService;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.util.Date;

@RequiredArgsConstructor
class BankTransferServiceTest {


  private final UBNBankTransferService bankTransferService;

    @Test
    void transferUBNtoUBN() {

        JobPayment transfer=new JobPayment();
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
        PaymentResponse response= bankTransferService.transferUBNtoUBN(transfer);

        Assert.assertEquals("00",response.getCode());
    }
}