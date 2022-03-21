package com.unionbankng.future.authorizationserver.pojos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPinRequest {
    @NotBlank
    private String token;

    @NotBlank
    private String newPin;
}
