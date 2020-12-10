package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

@Data
public class TokenConfirm {
    private Boolean success;
    private String message;
    private Long userId;
}
