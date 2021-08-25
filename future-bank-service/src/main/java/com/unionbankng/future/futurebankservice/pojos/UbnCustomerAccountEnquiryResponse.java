package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UbnCustomerAccountEnquiryResponse {
	 private UbnAccountEnquiryResponse account;
	 private UbnCustomerEnquiryResponse customer;
}