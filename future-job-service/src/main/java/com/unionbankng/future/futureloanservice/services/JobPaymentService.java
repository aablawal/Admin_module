package com.unionbankng.future.futureloanservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unionbankng.future.futurebankservice.grpc.UBNFundsTransferResponse;
import com.unionbankng.future.futureloanservice.entities.*;
import com.unionbankng.future.futureloanservice.enums.*;
import com.unionbankng.future.futureloanservice.pojos.APIResponse;
import com.unionbankng.future.futureloanservice.pojos.JwtUserDetail;
import com.unionbankng.future.futureloanservice.pojos.NotificationBody;
import com.unionbankng.future.futureloanservice.pojos.Payment;
import com.unionbankng.future.futureloanservice.repositories.*;
import com.unionbankng.future.futureloanservice.util.JWTUserDetailsExtractor;
import com.unionbankng.future.futureloanservice.util.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JobPaymentService implements Serializable {

    @Value("${sidekiq.accountName}")
    private String sidekiqAccountName;
    @Value("${sidekiq.accountNumber}")
    private String sidekiqAccountNumber;
    private Logger logger = LoggerFactory.getLogger(JobPaymentService.class);

    private final JobRepository jobRepository;
    private final JobContractRepository jobContractRepository;
    private final JobProposalRepository jobProposalRepository;
    private final BankTransferService bankTransferService;
    private final JobPaymentRepository jobPaymentRepository;

  public APIResponse makePayment(Payment payment) throws JsonProcessingException {

      //check if proposal is available
      JobProposal proposal = jobProposalRepository.findById(payment.getProposalId()).orElse(null);
      if (proposal == null) return new APIResponse("Proposal not found", false, null);

      //check if job is available
      Job job = jobRepository.findById(proposal.getJobId()).orElse(null);
      if (job == null) return new APIResponse("Job not found", false, null);


      UUID referenceId = UUID.randomUUID();
      String paymentReferenceId = referenceId.toString().replaceAll("-", "");

      //transfer back the charged amount to the Employer
      JobPayment transfer = new JobPayment();
      transfer.setUserId(proposal.getUserId());
      transfer.setJobId(proposal.getJobId());
      transfer.setProposalId(proposal.getId());
      transfer.setEmployerId(proposal.getEmployerId());
      transfer.setCreatedAt(new Date());
      //transfer
      transfer.setAmount(payment.getAmount());
      transfer.setCurrency("NGN");
      transfer.setPaymentReference(paymentReferenceId);
      transfer.setInitBranchCode("682");
      //debit the escrow amount
      transfer.setDebitAccountName(payment.getDebitAccountName());
      transfer.setDebitAccountNumber(payment.getDebitAccountNumber());
      transfer.setDebitAccountBranchCode("682");
      transfer.setDebitAccountType("CASA");
      transfer.setDebitNarration(payment.getNarration());
      //credit the employer account
      transfer.setCreditAccountName(payment.getCreditAccountName());
      transfer.setCreditAccountNumber(payment.getCreditAccountNumber());
      transfer.setCreditAccountBankCode("032");
      transfer.setCreditAccountBranchCode("682");
      transfer.setCreditAccountType("CASA");
      transfer.setCreditNarration(job.getTitle());

      logger.info(new ObjectMapper().writeValueAsString(transfer));
      UBNFundsTransferResponse transferResponse = bankTransferService.transferUBNtoUBN(transfer);
      if (transferResponse.getCode().compareTo("00") == 0) {
          //save the payment history
          jobPaymentRepository.save(transfer);
          logger.info("JOBSERVICE: Payment successful");
          return new APIResponse("Payment Successful", true, transferResponse);
      } else {
          String remark = transferResponse.getCode() + ": Payment Failed " + transferResponse.getMessage();
          logger.info("JOBSERVICE: Payment failed");
          logger.error(transferResponse.getMessage());
          logger.error(transferResponse.getCode());
          return new APIResponse(remark, false, null);
      }
  }
}

