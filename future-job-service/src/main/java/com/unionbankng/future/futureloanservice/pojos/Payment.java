package com.unionbankng.future.futureloanservice.pojos;

import lombok.*;
@Data
@Getter
@Setter
@NoArgsConstructor
public class Payment{

    protected int amount;
    protected Long proposalId;
    protected String debitAccountName;
    protected String debitAccountNumber;
    protected String creditAccountName;
    protected String creditAccountNumber;
    protected String narration;
}