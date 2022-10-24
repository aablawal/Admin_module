package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.ProfileType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProfileUpdateRequest {
    private ProfileType profileType;
    private Boolean isEmployer;
    private Boolean isFreelancer;
    private BigDecimal pricePerHour;
    private String jobTitle;
    private String phoneNumber;
    private String bio;
}
