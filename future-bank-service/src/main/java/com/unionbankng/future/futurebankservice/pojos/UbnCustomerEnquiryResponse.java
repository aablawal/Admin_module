package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UbnCustomerEnquiryResponse {

	private String code;
	private String message;
	private String reference;
	private String customerNumber;
	private String country;
	private String countryOfBirth;
	private String dob;
	private String nationality;
	private String lastName;
	private String firstName;
	private String otherNames;
	private String customerType;
	private String email;
	private String phoneNumber;
	private String idType;
	private String idNumber;
	private String countryOfIssue;
	private String effectiveDate;
	private String expiryDate;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String postalCode;
	private String bvn;
	private String gender;
}