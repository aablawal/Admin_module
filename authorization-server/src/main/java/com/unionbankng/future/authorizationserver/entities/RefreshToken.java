package com.unionbankng.future.authorizationserver.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class RefreshToken {

    @Id
    private String tokenId;
    private byte[] token;
    private byte[] authentication;
}

