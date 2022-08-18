package com.unionbankng.future.paymentservice.pojos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaystackSDKResponse {
    private String transactionRef;

    private BigDecimal amount;
}
