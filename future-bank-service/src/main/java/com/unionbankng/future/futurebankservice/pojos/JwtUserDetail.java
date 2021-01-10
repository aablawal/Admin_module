package com.unionbankng.future.futurebankservice.pojos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtUserDetail {

    private String userUUID;
    private String userEmail;
    private String userImg;
    private String userFullName;

}
