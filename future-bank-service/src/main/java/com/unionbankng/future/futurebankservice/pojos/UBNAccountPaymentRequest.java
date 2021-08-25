package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UBNAccountPaymentRequest{
    private String clientUrl;
    private String initiateAmount;
    private String oldAccNumber;
}