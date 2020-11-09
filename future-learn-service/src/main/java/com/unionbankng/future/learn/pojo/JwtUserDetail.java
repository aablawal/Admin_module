package com.unionbankng.future.learn.pojo;


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
