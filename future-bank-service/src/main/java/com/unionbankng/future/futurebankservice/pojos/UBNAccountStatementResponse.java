package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import java.util.List;

@Data
public class UBNAccountStatementResponse{
    private String code;
    private String message;
    private String reference;
    private String guid;
    private List<StatementItem> items;
}