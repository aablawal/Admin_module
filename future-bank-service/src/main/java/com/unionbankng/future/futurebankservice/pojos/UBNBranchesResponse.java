package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

import java.util.List;

@Data
public class UBNBranchesResponse{
    private String statusCode;
    private String statusMessage;
    private List<UBNBranch> data;
}