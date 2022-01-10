package com.unionbankng.future.authorizationserver.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_address_verifications")
@Getter
@Setter
public class KycAddressVerification implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long resID;
    private String firstname;
    private String lastname;
    private String phone;
    private String idType;
    private String idNumber;
    private String middlename;
    private String photo;
    private String gender;
    private String birthdate;
    private String lattitude;
    private String longitude;
    private String status;
    private String subStatus;
    private String city;
    private String street;
    private String lga;
    private String country;
    private String reference;
    private String state;
    private String userId;
    private String docType;
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
        return idType+" "+idNumber;
    }

}
