package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UBNCompleteAccountPaymentResponse{
    private String statusCode;
    private String statusMessage;
    private String data;
}