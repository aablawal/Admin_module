package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.UserType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegistrationRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String dialingCode;
    @NotNull
    private String phoneNumber;
    @NotNull
    private UserType userType;
}
