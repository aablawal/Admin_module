package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

@Data
public class AddressVerificationRequestVerifyme {
    private String callbackUrl;
    private String street;
    private String lga;
    private String state;
    private String landmark;
    private Applicant applicant;
    private String userId;
    private String idImage;
}
