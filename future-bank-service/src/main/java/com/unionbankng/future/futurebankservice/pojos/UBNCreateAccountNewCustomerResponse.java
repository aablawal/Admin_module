package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UBNCreateAccountNewCustomerResponse {

    private String statusCode;
    private String statusMessage;
    private UBNAccount data;

}