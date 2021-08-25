package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import java.util.List;

@Data
public class UBNAccountTypeResponse{
    private String statusCode;
    private String statusMessage;
    private List<String> data;
    private boolean accountText;
}