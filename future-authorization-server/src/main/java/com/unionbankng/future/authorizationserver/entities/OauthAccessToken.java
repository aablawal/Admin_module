package com.unionbankng.future.authorizationserver.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
public class OauthAccessToken {
    @Id
    private String authenticationId;
    @Column(columnDefinition="TEXT")
    private String tokenId;
    private byte[] token;
    private String userName;
    private String clientId;
    private byte[] authentication;
    @Column(columnDefinition="TEXT")
    private String refreshToken;


}