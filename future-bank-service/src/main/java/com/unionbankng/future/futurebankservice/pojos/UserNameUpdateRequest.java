package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UserNameUpdateRequest {
    private String accountCode;
    private String name;
}