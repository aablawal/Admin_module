package com.unionbankng.future.paymentservice.pojos;

import lombok.Data;

@Data
public class InitiateFundingRequest {

    private String merchantCode;

    private String payItemID;

    private String amount;

    private String transactionReference;

    private String customerEmail;

    private String customerName;

    private String paymentMode;

    private String payItemName;

    private String customerID;

}
