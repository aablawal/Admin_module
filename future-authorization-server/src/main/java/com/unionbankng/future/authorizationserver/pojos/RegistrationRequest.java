package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.UserType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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
    @Size(min = 6)
    private String username;
    @NotNull
    private String dialingCode;
    @NotNull
    private String phoneNumber;
    @NotNull
    private UserType userType;
    private String address;
    @Size(min = 3,max = 3)
    private String country;
    @Size(max = 50)
    private String stateOfResidence;
    private Date dateOfBirth;
}
