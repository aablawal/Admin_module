package com.unionbankng.future.authorizationserver.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
public class OauthRefreshToken {

    @Id
    private String tokenId;
    private byte[] token;
    private byte[] authentication;
}

