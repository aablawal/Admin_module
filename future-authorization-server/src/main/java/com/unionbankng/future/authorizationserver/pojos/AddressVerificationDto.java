package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.AddressStatus;
import lombok.Data;

@Data
public class AddressVerificationDto {
    private String street;
    private String lga;
    private String state;
    private String city;
    private String country;
    private String landmark;
    private String idNumber;
    private String firstname;
    private String lastname;
    private String phone;
    private String dob;
    private String userId;
    private Long resID;
    private String clientRef;
    private String reference;
    private String callbackUrl;
    private String vendor;
    private String idType;
    private AddressStatus status;
}
