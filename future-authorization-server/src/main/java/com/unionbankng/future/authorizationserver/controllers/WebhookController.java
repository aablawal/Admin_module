package com.unionbankng.future.authorizationserver.controllers;

import com.unionbankng.future.authorizationserver.pojos.*;
import com.unionbankng.future.authorizationserver.services.KYCService;
import com.unionbankng.future.authorizationserver.utils.App;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/webhooks/")
@RequiredArgsConstructor
public class WebhookController {
    private final MessageSource messageSource;
    private final KYCService kycService;
    private final App app;


    @PostMapping("/v1/kyc/verifyme/webhook")
    public APIResponse<String> verifyAddress(@RequestBody AddressVerificationWebhookRequest addressVerificationWebhookRequest) {
        app.print("Webhook received: " + addressVerificationWebhookRequest.toString());
        return kycService.processWebhookRequest(addressVerificationWebhookRequest);
    }
}
