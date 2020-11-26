package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class ProductDetailsResponse{
    private String statusCode;
    private String statusMessage;
    private ProductDetailsData data;
    private boolean accountText;
}
