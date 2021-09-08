package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UbnCustomerEnquiryRequest {
	
	private String accountNumber;
	private String accountType;
}