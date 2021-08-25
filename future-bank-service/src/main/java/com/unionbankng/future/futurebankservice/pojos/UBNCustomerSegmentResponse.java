package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import java.util.List;

@Data
public class UBNCustomerSegmentResponse{
    private String statusCode;
    private String statusMessage;
    private List<CustomerSegment> data;
    private boolean accountText;
}