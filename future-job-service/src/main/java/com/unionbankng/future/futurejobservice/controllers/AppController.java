package com.unionbankng.future.futurejobservice.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.unionbankng.future.futurejobservice.pojos.APIResponse;
import com.unionbankng.future.futurejobservice.entities.JobBulkPayment;
import com.unionbankng.future.futurejobservice.pojos.PaymentRequest;
import com.unionbankng.future.futurejobservice.services.JobPaymentService;
import com.unionbankng.future.futurejobservice.util.App;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class AppController {

    private final JobPaymentService jobPaymentService;
    private final App app;

    @GetMapping("/v1/ping")
    public ResponseEntity<APIResponse<String>> pingService(){
        app.print("Pinging....");
        return ResponseEntity.ok().body( new APIResponse("Service is Up", true, "Live"));
    }

    @PostMapping("/v1/test")
    public ResponseEntity<APIResponse<String>> testService(){
        return ResponseEntity.ok().body(new APIResponse("Nothing to test", true, "Test  result goes here"));
    }

    @PostMapping("/v1/bulk/bank_transfer/test")
    public ResponseEntity<APIResponse<String>> testBulkTransfer(){

        ArrayList<JobBulkPayment> bulkPayments=new ArrayList<>();
        JobBulkPayment bank1= new JobBulkPayment();
        bank1.setTransactionId("1");
        bank1.setAccountNumber("0055982543");
        bank1.setAccountType("CASA");
        bank1.setAccountName("DEDICATED NEFT O A");
        bank1.setNarration("My first testing");
        bank1.setAmount(11);
        bank1.setCrDrFlag("D");
        bank1.setExecutedBy("Test User");
        bank1.setExecutedFor("Test Something");
        bank1.setPaymentReference("Testiksjdjskdjksjd");
        bulkPayments.add(bank1);


        JobBulkPayment bank2= new JobBulkPayment();
        bank2.setTransactionId("2");
        bank2.setAccountNumber("0095788138");
        bank2.setAccountType("CASA");
        bank2.setAccountName("UNKNOWN USER");
        bank2.setNarration("My first testing");
        bank2.setAmount(11);
        bank2.setCrDrFlag("C");
        bank2.setExecutedBy("Test User");
        bank2.setExecutedFor("Test Something");
        bank2.setPaymentReference("Testiksjdjskdjksjd");
        bulkPayments.add(bank2);
        APIResponse response=jobPaymentService.makeBulkPayment(bulkPayments);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/v1/bank/transfer/test")
    public ResponseEntity<APIResponse<String>> bankTransfer() throws JsonProcessingException {


//        250700012
//        WESTERN UNION SETTLEMENT A/C -IMPLEMENT
//        315200043
//        WESTERN UNION-COMMISSION

        PaymentRequest payment = new PaymentRequest();
        payment.setAmount(20);
        payment.setNarration("Testing Narration");
        payment.setCreditAccountName("CPC CASH DEPOSIT ACCOUNT IMPLEMENT");
        payment.setCreditAccountNumber("210210071");
        payment.setDebitAccountName("WESTERN UNION-COMMISSION");
        payment.setDebitAccountNumber("315200043");
        payment.setExecutedBy("Test User");
        payment.setExecutedFor("Test");
        payment.setPaymentReference("skdjuw9jskdjiwuei");
        APIResponse response= jobPaymentService.makePayment(payment);
        return ResponseEntity.ok().body(response);

    }

}
