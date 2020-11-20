package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class AccountIdTypesResponse {
    private String statusCode;
    private String statusMessage;
    private AccountIdTypesData data;
}
