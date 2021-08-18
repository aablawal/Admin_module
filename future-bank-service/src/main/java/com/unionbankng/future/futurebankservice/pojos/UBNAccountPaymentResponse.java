package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UBNAccountPaymentResponse{
    private String statusCode;
    private String statusMessage;
    private UBNAccountPayment data;
}