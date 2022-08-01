package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.IdType;
import lombok.Data;

@Data
public class VerifyKycRequest {
    private IdType idType;
    private String idNumber;
    private String gender;
    private  String dob;
    private String phoneNumber;
}
