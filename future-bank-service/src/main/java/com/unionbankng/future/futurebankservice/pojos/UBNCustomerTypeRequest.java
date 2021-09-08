package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import java.util.Map;

@Data
public class UBNCustomerTypeRequest{
    private String statusCode;
    private String statusMessage;
    private Map<String,String> data;
    private boolean accountText;
}