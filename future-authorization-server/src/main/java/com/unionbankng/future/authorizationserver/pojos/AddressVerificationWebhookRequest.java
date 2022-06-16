package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

@Data
public class AddressVerificationWebhookRequest {
    private String type;
    private AddressVerificationWebhookData data;
}
