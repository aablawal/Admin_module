package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UBNAccountPayment{
    private String reference;
    private String authorizationUrl;
    private Double amount;
    private boolean paymentCompletd;
}