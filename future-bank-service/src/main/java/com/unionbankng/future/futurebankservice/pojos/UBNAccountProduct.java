package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import java.util.List;

@Data
public class UBNAccountProduct {
    private int id;
    private String code;
    private String name;
    private int tier;
    private boolean idCardRequired;
    private AccountType accountType;
    private String description;
    private List<DocumentType> documentTypes;
    private List<ProductDes> productDes;
    private boolean forAdult;
}