package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

@Data
public class AddressVerifyResponse<T> {
    private String message;
    private String code;
    private boolean success;
    private T data;

    public AddressVerifyResponse(String message, boolean success, String code, T data) {
        super();
        this.message = message;
        this.success = success;
        this.code = code;
        this.data = data;
    }
}
