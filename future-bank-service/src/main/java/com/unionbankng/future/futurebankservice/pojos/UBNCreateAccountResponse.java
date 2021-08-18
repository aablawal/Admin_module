package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UBNCreateAccountResponse {

    private String code;
    private String message;
    private String reference;
    private String customerId;
    private String accountNumber;

}