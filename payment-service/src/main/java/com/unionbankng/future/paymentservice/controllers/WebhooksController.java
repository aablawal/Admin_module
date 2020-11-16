package com.unionbankng.future.paymentservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.future.paymentservice.gateways.Paystack;
import com.unionbankng.future.paymentservice.pojos.PaystackTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks")
@RequiredArgsConstructor
public class WebhooksController {

    private final Paystack paystack;
    private final ObjectMapper objectMapper;


    @Value("${paystack.secret.key}")
    private String paystackSecretKey;



    @PostMapping("v1/paystack")
    public ResponseEntity<String> paystackTransfer(@RequestBody String request , @RequestHeader HttpHeaders headers) throws Exception{

        String authToken = headers.getFirst("x-paystack-signature");


        if(!paystack.validatePayment(new String[] { request, authToken , paystackSecretKey }))
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);


        PaystackTransaction paystackTransaction = objectMapper.readValue(request,PaystackTransaction.class);

        return paystack.completePayment(paystackTransaction);

    }
}
