package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import java.util.List;

@Data
public class UBNGetAccountsResponse {
    private String code;
    private String message;
    private String reference;
    private List<Object> accounts;
}