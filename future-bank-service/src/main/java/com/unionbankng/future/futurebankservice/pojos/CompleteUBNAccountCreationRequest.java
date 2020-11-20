package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class CompleteUBNAccountCreationRequest{
    private Long customerRecordId;
    private String customerType;
    private String introducerTag;
}