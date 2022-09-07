package com.unionbankng.future.paymentservice.pojos;

import lombok.Data;

@Data
public class PaystackSDKResponse {
    private String walletId;
    private String customerName;
    private String customerEmail;
    private String transactionReference;
    private String amount;
}
