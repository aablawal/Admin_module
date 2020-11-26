package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import java.util.Map;

@Data
public class UBNGenericResponse {
    private String statusCode;
    private String statusMessage;
}