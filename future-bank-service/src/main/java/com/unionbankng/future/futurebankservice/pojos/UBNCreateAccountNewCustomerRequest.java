package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UBNCreateAccountNewCustomerRequest {

	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String dateOfBirth;
	private String address;
	private String address2;
	private String address3;
	private String address4;
	private String state;
	private String nationality; //NG
	private String motherMaidenName;
	private String telephone;
	private String telephone2;
	private String profession;
	private String maritaStatus; //MARRIED
	private String gender;//F
	private String email;
	private String custIdCardNo;//yu78665w
	private String issueDate; //21-jan-2000"
	private String expiryDate; //28-dec-2023,
	private String nextOfKin; // name ahmed anslem
	private String relatioship; //BROTHER,
	private String customerBranchCode; //682,
	private String uniqueID; //CHUKSMOHAMMED126,
	private String customerCategory; //IND_IND
	private String customerType; //I,
	private String language; // ENG,
	private String city;
	private String countryOfResident; //NG,
	private String zipCode;
	private boolean staff = false;
	private String country; //NG,
	private String nextOfKinMobileNumber;
	private boolean smsAlert = false;
	private String rmCode; //5429540,
	private String initiatorID; //WEBSVCS
	private String verifierID; //WEBSVCS
	private String misCode;
	private String misClass;
	private String introducerTag; //4626183;
	private String accountBranchCode; //682;
	private String ccyCode;
	private String productCode;
	private boolean pnd = false;
	private String externalReference;

	
}