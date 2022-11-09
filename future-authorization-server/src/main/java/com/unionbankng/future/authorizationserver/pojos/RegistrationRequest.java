package com.unionbankng.future.authorizationserver.pojos;

import com.unionbankng.future.authorizationserver.enums.AuthProvider;
import com.unionbankng.future.authorizationserver.enums.ProfileType;
import com.unionbankng.future.authorizationserver.enums.Role;
import com.unionbankng.future.authorizationserver.interfaces.ThirdPartyOauthProvider;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @NotNull
    @Size(min = 6)
    private String username;
    private String dialingCode;
    private String phoneNumber;
    private String thirdPartyToken;
    @NotNull
    private AuthProvider authProvider;
    private String department;
    private Role role;
    private String branchCode;
    private String roleFunction;



}
