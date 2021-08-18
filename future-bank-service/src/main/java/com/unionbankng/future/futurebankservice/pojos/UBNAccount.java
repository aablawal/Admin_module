package com.unionbankng.future.futurebankservice.pojos;

import lombok.Data;

@Data
public class UBNAccount{
    private Long id;
    private String bvn;
    private String title;
    private String firstName;
    private String lastName;
    private String gender;
    private String telephone;
    private String telephone2;
    private String dateOfBirth;
    private String email;
    private String middleName;
    private String address;
    private String address2;
    private String address3;
    private String address4;
    private String country;
    private String state;
    private String nationality;
    private String maidenName;
    private String nextOfKin;
    private String nextOfKinPhone;
    private String idType;
    private String cusIdCardNo;
    private String customerBranchCode;
    private String annualIncome;
    private String initialAmount;
    private boolean smsAlert;
    private boolean postNodebit;
    private boolean staff;
    private String accountBranchCode;
    private String profession;
    private String maritaStatus;
    private String relationship;
    private String language;
    private String city;
    private String countryOfResident;
    private String zipCode;
    private String ccyCode;
    private boolean paymentSuccessful;
    private boolean isAccountFunded;
    private int fundTrialCount;
    private boolean archiveCompleted;
    private boolean mandateCreated;
    private boolean docSubmitted;
    private UBNAccountProduct product;
}