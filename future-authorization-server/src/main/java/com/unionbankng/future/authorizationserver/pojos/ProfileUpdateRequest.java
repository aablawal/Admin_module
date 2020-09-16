package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProfileUpdateRequest {
    private String bio;
    private String firstName;
    private String lastName;
    private String dialingCode;
    private String phoneNumber;
    private String address;
    private String country;
    private String stateOfResidence;
    private Date dateOfBirth;
    private BigDecimal pricePerHour;
    private String jobTitle;
}
