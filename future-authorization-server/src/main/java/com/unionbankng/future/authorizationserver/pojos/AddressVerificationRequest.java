package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

@Data
public class AddressVerificationRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String dob;
    private String buildingNumber;
    private String street;
    private String landmark;
    private String lga;
    private String state;
    private String city;
    private String country;
    private String image;
    private String mobile;
}
