package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import java.util.List;

@Data
public class UBNCountryResponse{
    private String statusCode;
    private String statusMessage;
    private List<UBNCountryData> data;
}