package com.unionbankng.future.authorizationserver.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_identity_verifications")
@Getter
@Setter
public class Kyc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String LastName;
    private String Dob;
    private String idType;
    private String idNUmber;
    private String idExpiry;
    private String idImage;
    private String selfieImage;
    private Boolean verificationStatus;
    private String userId;
    private String score;
    private String verdict;
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void setUpdatedAt() {
        updatedAt = new Date();
    }


    @Override
    public boolean equals(Object device) {
        return this.id.equals(((Kyc)device).getId());

    }

    @Override
    public String toString(){
        return idType+" "+idNUmber;
    }

}
