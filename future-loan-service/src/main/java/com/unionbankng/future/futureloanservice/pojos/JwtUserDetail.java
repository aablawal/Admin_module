package com.unionbankng.future.futureloanservice.pojos;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtUserDetail {

    private Long userId;
    private String userUUID;
    private String userEmail;
    private String userImg;
    private String userFullName;

}
