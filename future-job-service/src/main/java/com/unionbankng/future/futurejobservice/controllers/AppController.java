package com.unionbankng.future.futurejobservice.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.unionbankng.future.futurejobservice.entities.Test;
import com.unionbankng.future.futurejobservice.pojos.*;
import com.unionbankng.future.futurejobservice.entities.JobBulkPayment;
import com.unionbankng.future.futurejobservice.repositories.TestRepository;
import com.unionbankng.future.futurejobservice.services.JobPaymentService;
import com.unionbankng.future.futurejobservice.services.TestService;
import com.unionbankng.future.futurejobservice.services.UserWalletService;
import com.unionbankng.future.futurejobservice.util.App;
import com.unionbankng.future.futurejobservice.util.AppLogger;
import com.unionbankng.future.futurejobservice.util.JWTUserDetailsExtractor;
import com.unionbankng.future.futurejobservice.util.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class AppController {

    private final UserWalletService walletService;
    private final JobPaymentService jobPaymentService;
    private final NotificationSender notificationSender;
    private final TestService testService;
    private final AppLogger appLogger;
    private final App app;

    @GetMapping("/v1/ping")
    public ResponseEntity<APIResponse<String>> pingService(){
        app.print("Pinging....");
        return ResponseEntity.ok().body( new APIResponse("Service is Up", true, "Live"));
    }
    @PostMapping("/v1/test")
    public ResponseEntity<APIResponse<Test>> testService(@RequestBody Test test){
        return ResponseEntity.ok().body(new APIResponse("Request Successful", true, walletService.getAuth()));
    }
    @PostMapping("/v1/notification/test")
    public ResponseEntity<APIResponse<String>> testNotificationService(OAuth2Authentication authentication){
        NotificationBody body = new NotificationBody();
        body.setBody("Testing notification");
        body.setSubject("Test");
        body.setActionType("REDIRECT");
        body.setAction("https://example.com");
        body.setTopic("Job");
        body.setChannel("S");
        body.setPriority("YES");
        body.setRecipientEmail("net.rabiualiyu@gmail.com");
        body.setRecipientName("Rabiu Abdul Aliyu");
        body.setRecipient(1l);
        notificationSender.pushNotification(body);
        return ResponseEntity.ok().body(new APIResponse("Notification Fired", true, body));
    }

    @PostMapping("/v1/bulk/bank_transfer/test")
    public ResponseEntity<APIResponse<String>> testBulkTransfer(){

        ArrayList<JobBulkPayment> bulkPayments=new ArrayList<>();
        JobBulkPayment bank1= new JobBulkPayment();
        bank1.setTransactionId("1");
        bank1.setAccountNumber("210210071");
        bank1.setAccountType("GL");
        bank1.setAccountName("CPC CASH DEPOSIT ACCOUNT IMPLEMENT");
        bank1.setNarration("My first testing");
        bank1.setAmount(11);
        bank1.setCrDrFlag("D");
        bank1.setExecutedBy("Test User");
        bank1.setExecutedFor("Test Something");
        bank1.setPaymentReference("Testiksjdjskdjksjd");
        bulkPayments.add(bank1);

        JobBulkPayment bank2= new JobBulkPayment();
        bank2.setTransactionId("2");
        bank2.setAccountNumber("315200043");
        bank2.setAccountType("GL");
        bank2.setAccountName("WESTERN UNION-COMMISSION");
        bank2.setNarration("My first testing");
        bank2.setAmount(11);
        bank2.setCrDrFlag("C");
        bank2.setExecutedBy("Test User");
        bank2.setExecutedFor("Test Something");
        bank2.setPaymentReference("tolustest");
        bulkPayments.add(bank2);
        APIResponse response=jobPaymentService.makeBulkPayment(bulkPayments);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/v1/bank/transfer/test")
    public ResponseEntity<APIResponse<String>> bankTransfer() throws JsonProcessingException {
        PaymentRequest payment = new PaymentRequest();
        payment.setAmount(20);
        payment.setNarration("Testing Narration");
        payment.setCreditAccountName("GL M36 FLOAT ACCOUNT");
        payment.setCreditAccountNumber("250990140");
        payment.setDebitAccountName("OLANLOKUN LANRE");
        payment.setDebitAccountNumber("0040553624");
        payment.setExecutedBy("Test User");
        payment.setExecutedFor("Test");
        payment.setDebitAccountType("CASA");
        payment.setCreditAccountType("GL");
        payment.setPaymentReference("skdjkasjdksjkadj");
        APIResponse response= jobPaymentService.makePayment(payment);
        return ResponseEntity.ok().body(response);

    }

}
