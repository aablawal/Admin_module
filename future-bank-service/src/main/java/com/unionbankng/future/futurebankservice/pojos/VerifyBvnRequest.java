package com.unionbankng.future.futurebankservice.pojos;


import lombok.Data;

@Data
public class VerifyBvnRequest {
	private String bvn;
	private String otp;
}