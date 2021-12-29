package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.ProfileType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProfileUpdateRequest {
    private long userId;
    private ProfileType profileType;
    private Boolean isEmployer;
    private Boolean isFreelancer;
    private BigDecimal pricePerHour;
    private String jobTitle;
    private String phoneNumber;
    private  String bio;
}
