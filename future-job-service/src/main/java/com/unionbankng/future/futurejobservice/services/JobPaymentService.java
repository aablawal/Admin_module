package com.unionbankng.future.futurejobservice.services;

import com.unionbankng.future.futurebankservice.grpc.UBNBulkFundTransferBatchItem;
import com.unionbankng.future.futurebankservice.grpc.UBNBulkFundsTransferRequest;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferResponse;
import com.unionbankng.future.futurejobservice.entities.*;
import com.unionbankng.future.futurejobservice.pojos.*;
import com.unionbankng.future.futurejobservice.repositories.*;
import com.unionbankng.future.futurejobservice.util.App;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JobPaymentService implements Serializable {

    private Logger logger = LoggerFactory.getLogger(JobPaymentService.class);
    private final BankTransferService bankTransferService;
    private final JobPaymentRepository jobPaymentRepository;
    private final JobBulkPaymentRepository jobBulkPaymentRepository;
    private final App app;

  public APIResponse makePayment(PaymentRequest paymentRequest) {

      //transfer back the charged amount to the Employer
      JobPayment payment = new JobPayment();
      payment.setAmount(paymentRequest.getAmount());
      payment.setCurrency("NGN");
      payment.setPaymentReference(paymentRequest.getPaymentReference());
      payment.setExecutedBy(paymentRequest.getExecutedBy());
      payment.setExecutedFor(paymentRequest.getExecutedFor());
      payment.setInitBranchCode("682");
      //debit the escrow amount
      payment.setDebitAccountName(paymentRequest.getDebitAccountName());
      payment.setDebitAccountNumber(paymentRequest.getDebitAccountNumber());
      payment.setDebitAccountBranchCode("682");
      payment.setDebitAccountType(paymentRequest.getDebitAccountType()!=null?paymentRequest.getDebitAccountType():"CASA");
      payment.setDebitNarration(paymentRequest.getNarration());
      //credit the employer account
      payment.setCreditAccountName(paymentRequest.getCreditAccountName());
      payment.setCreditAccountNumber(paymentRequest.getCreditAccountNumber());
      payment.setCreditAccountBankCode("032");
      payment.setCreditAccountBranchCode("682");
      payment.setCreditAccountType(paymentRequest.getCreditAccountType()!=null?paymentRequest.getDebitAccountType():"CASA");
      payment.setCreditNarration(paymentRequest.getNarration());

      PaymentResponse transferResponse = bankTransferService.transferUBNtoUBN(payment);
      if (transferResponse.getCode().compareTo("00") == 0) {
          //save the payment history
          payment.setPaymentReference(transferResponse.getReference());
          jobPaymentRepository.save(payment);
          logger.info("JOBSERVICE: Payment successful");
          return new APIResponse("Payment Successful", true, transferResponse.getReference());
      } else {
          String remark = transferResponse.getCode() + ": Payment Failed " + transferResponse.getMessage();
          logger.info("JOBSERVICE: Payment failed");
          logger.error(transferResponse.getMessage());
          logger.error(transferResponse.getCode());
          return new APIResponse(remark, false, transferResponse);
      }
  }
    public APIResponse makeBulkPayment(ArrayList<JobBulkPayment> bulkPaymentBatchItems){

        ArrayList<UBNBulkFundTransferBatchItem> batchItems= new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String referenceId="BULK"+app.makeUIID().substring(0,5);

        for(JobBulkPayment batchItem: bulkPaymentBatchItems){
            UBNBulkFundTransferBatchItem bank=UBNBulkFundTransferBatchItem.newBuilder()
                    .setAccountNumber(batchItem.getAccountNumber())
                    .setAccountType(batchItem.getAccountType())
                    .setAccountName(batchItem.getAccountName())
                    .setAccountBranchCode("000")
                    .setAccountBankCode("032")
                    .setNarration(batchItem.getNarration())
                    .setInstrumentNumber("")
                    .setAmount(String.valueOf(batchItem.getAmount()))
                    .setValueDate(simpleDateFormat.format(new Date()))
                    .setCrDrFlag(batchItem.getCrDrFlag())
                    .setFeeOrCharges("false").build();
            batchItems.add(bank);
        }
        UBNBulkFundsTransferRequest request = UBNBulkFundsTransferRequest.newBuilder()
                .setCurrency("NGN")
                .setPaymentReference(referenceId)
                .setInitBranchCode("000")
                .setTransactionDate(simpleDateFormat.format(new Date()))
                .setPaymentTypeCode("FT")
                .setExternalSystemReference("")
                .addAllBatchItems(batchItems).build();

        PaymentResponse transferResponse = bankTransferService.transferBulkUBNtoUBN(request);
        if (transferResponse.getCode().compareTo("00") == 0) {

            for(JobBulkPayment batchItem: bulkPaymentBatchItems){
                batchItem.setPaymentReference(transferResponse.getReference());
            }
            jobBulkPaymentRepository.saveAll(bulkPaymentBatchItems);
            logger.info("JOBSERVICE: Payment successful");
            return new APIResponse("Payment Successful", true, transferResponse.getReference());
        } else {
            String remark = transferResponse.getCode() +": Payment Failed " + transferResponse.getMessage();
            logger.info("JOBSERVICE: Payment failed");
            logger.error(transferResponse.getMessage());
            logger.error(transferResponse.getCode());
            return new APIResponse(remark, false, transferResponse);
        }
    }
}

