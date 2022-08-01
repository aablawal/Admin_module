package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.IdType;
import lombok.Data;

@Data
public class IdRequest {
    private IdType idType;
    private String idNumber;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String dob;
    private String gender;
    private String photo;
    private String country = "NG";
    private String userId;
}
