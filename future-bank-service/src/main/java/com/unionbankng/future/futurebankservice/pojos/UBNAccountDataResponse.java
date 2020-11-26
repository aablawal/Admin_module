package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UBNAccountDataResponse {
 private String statusCode;
 private String statusMessage;
 private UBNAccountData data;
 private boolean accountText;
 
}