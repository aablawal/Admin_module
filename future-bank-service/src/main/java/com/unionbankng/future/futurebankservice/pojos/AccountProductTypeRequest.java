package com.unionbankng.future.futurebankservice.pojos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountProductTypeRequest{
    private boolean adult;
    private boolean hasValidId;
    private String idCard;
}