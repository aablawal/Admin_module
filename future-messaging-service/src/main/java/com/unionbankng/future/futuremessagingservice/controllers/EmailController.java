package com.unionbankng.future.futuremessagingservice.controllers;

import com.unionbankng.future.futuremessagingservice.pojos.APIResponse;
import com.unionbankng.future.futuremessagingservice.pojos.EmailBody;
import com.unionbankng.future.futuremessagingservice.pojos.SMS;
import com.unionbankng.future.futuremessagingservice.pojos.SendGridEmailBody;
import com.unionbankng.future.futuremessagingservice.services.DirectIPSMSService;
import com.unionbankng.future.futuremessagingservice.services.SendGridEmailService;
import com.unionbankng.future.futuremessagingservice.smsandemailproviders.UnionEmailProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api")
public class EmailController {

    private final UnionEmailProvider unionEmailProvider;

    @PostMapping(value = "/v1/email/send_email")
    public ResponseEntity<APIResponse> sendEmail(@RequestBody EmailBody body) throws Exception {
        unionEmailProvider.send(body);
        return ResponseEntity.ok().body(new APIResponse("Message sent",true,null));
    }
}
