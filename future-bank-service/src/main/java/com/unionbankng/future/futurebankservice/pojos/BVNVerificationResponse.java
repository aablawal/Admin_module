package com.unionbankng.future.futurebankservice.pojos;


import lombok.Data;

@Data 
public class BVNVerificationResponse {
	 private String statusCode;
	 private String statusMessage;
	 private ValidateBvnResponse data;
	 private String accountText;
}