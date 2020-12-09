package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UBNAccountCreationRequest {
    private Long customerRecordId;
    private String customerType;
    private String introducerTag;
    private String paymentNotRequired;
}