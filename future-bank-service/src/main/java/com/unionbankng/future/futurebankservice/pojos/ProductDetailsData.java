package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailsData{
    private String code;
    private String name;
    private String customerCategory;
    private int tier;
    private boolean idCardRequired;
    private Boolean canBeReactivated;
    private AccountType accountType;
    private String description;
    private List<DocumentType> documentTypes;
    private List<ProductDes> productDes;
    private boolean forAdult;
}