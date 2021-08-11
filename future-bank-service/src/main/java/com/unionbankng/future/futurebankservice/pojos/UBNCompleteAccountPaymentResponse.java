package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UBNCompleteAccountPaymentResponse{
    private String statusCode;
    private String statusMessage;
    private UBNCompleteAccountResponseData data;
    private String accountText;
}