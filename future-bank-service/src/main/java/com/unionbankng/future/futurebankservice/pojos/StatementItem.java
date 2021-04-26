package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatementItem{
    private long trnDate;
    private long postDate;
    private int valueDate;
    private String narration;
    private String reference;
    private String ccy;
    private String drcrFlag;
    private BigDecimal crAmount;
    private BigDecimal drAmount;
    private BigDecimal runningBalance;
}