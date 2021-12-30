package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

@Data
public class AddressVerificationRequestVerifyme<T> {
    private String street;
    private String lga;
    private String state;
    private String landmark;
    private Applicant applicant;
//    private String docType;
//    private String idImage;
//    private String reference;
}
