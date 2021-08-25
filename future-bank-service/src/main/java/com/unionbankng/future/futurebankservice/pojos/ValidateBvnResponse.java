package com.unionbankng.future.futurebankservice.pojos;


import lombok.Data;

@Data 
public class ValidateBvnResponse {
	 private String responseCode;
	 private String email;
	 private String maritalStatus;
	 private String gender;
	 private String stateOfOrigin;
	 private String residentialAdd;
	 private String lga;
	 private String title;
	 private String firstName;
	 private String lastName;
	 private String middleName;
	 private String dateOfBirth;
	 private String branchEnroll;
	 private String watchListed;
	 private String phoneNumber;
	 private String imageHashSet;
	 private String bankEnroll;
}