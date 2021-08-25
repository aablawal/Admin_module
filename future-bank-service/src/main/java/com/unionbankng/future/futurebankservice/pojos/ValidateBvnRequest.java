package com.unionbankng.future.futurebankservice.pojos;


import lombok.Data;

@Data
public class ValidateBvnRequest {
	private String bvn;
	private String accountTier;
	private String dob;
}