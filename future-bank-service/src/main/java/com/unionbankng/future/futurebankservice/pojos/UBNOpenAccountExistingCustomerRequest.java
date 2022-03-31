package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

public @Data class UBNOpenAccountExistingCustomerRequest {


	private String firstName;
	private String middleName;
	private String lastName;
	private String city;
	private String initiatorID; //WEBSVCS
	private String verifierID; //WEBSVCS
	private String accountBranchCode; //000;
	private String ccyCode;
	private String customerId;
	private String productCode;
	private boolean pnd = false;
	private String rmCode; //5429540,
	private String externalReference;
	
}