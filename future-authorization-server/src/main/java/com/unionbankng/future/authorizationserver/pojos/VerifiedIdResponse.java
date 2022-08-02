package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.IdType;
import com.unionbankng.future.authorizationserver.enums.VerificationStatus;
import lombok.Data;

import java.util.Date;

@Data
public class VerifiedIdResponse<T> {
    private IdType idType;
    private String idNumber;
    private String client;
    private String provider;
    private String userId;
    private VerificationStatus status;
    private String transactionStatus;
    private String transactionReference;
    private String transactionDate;
    private String firstName;
    private String lastName;
    private String middleName;
    private String dob;
    private String photoUrl;
    private String mobile;
    private String gender;
    private String issuedAt;
    private String issuedDate;
    private String expiryDate;
    private String score;
    private String verdict;
    private Date createdAt;
    private Date updatedAt;
}
