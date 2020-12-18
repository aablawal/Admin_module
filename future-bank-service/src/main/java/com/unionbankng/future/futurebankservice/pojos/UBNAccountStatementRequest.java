package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UBNAccountStatementRequest {

    private String accountNumber;
    private String endDate;
    private String startDate;
}
