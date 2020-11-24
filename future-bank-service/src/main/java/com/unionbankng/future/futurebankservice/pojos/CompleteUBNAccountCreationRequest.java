package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompleteUBNAccountCreationRequest{
    @NotNull
    private Long customerRecordId;
    @NotNull
    private String customerType;
    @NotNull
    private String introducerTag;
    @NotNull
    private String accountName;
}