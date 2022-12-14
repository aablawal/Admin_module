package com.unionbankng.future.authorizationserver.pojos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest<T> {
    private String username;
    private String password;
}
