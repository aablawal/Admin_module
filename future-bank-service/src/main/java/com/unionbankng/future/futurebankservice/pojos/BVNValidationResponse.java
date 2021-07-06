package com.unionbankng.future.futurebankservice.pojos;


import lombok.Data;

@Data 
public class BVNValidationResponse {
	 private String statusCode;
	 private String statusMessage;
	 private String accountText;
}