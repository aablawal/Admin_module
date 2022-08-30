package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

@Data
public class VerifyAddressRequest {
    private String userId;
    private String callbackUrl;
    private String street;
    private String city;
    private String lga;
    private String state;
    private String country;
    private String landmark;
    private Integer buildingNumber =  100;
    private String idNumber;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String dob;
    private String clientRef;
    private String image;
}
