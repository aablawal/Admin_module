package com.unionbankng.future.futurebankservice.pojos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BVNVerificationResponse {
	 private String statusCode;
	 private String statusMessage;
	 private ValidateBvnResponse data;
	 private String accountText;
}