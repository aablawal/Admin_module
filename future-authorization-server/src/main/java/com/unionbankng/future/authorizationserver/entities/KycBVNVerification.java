package com.unionbankng.future.authorizationserver.entities;

import com.unionbankng.future.authorizationserver.enums.VerificationStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_bvn_verifications")
@Getter
@Setter
public class KycBVNVerification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String dob;

    private String userUuid;

    private Long userId;

    private String bvn;

    @Enumerated(EnumType.STRING)
    private VerificationStatus status;

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
    public boolean equals(Object obj) {
        if(obj instanceof KycBVNVerification) {
            return this.id.equals(((KycBVNVerification) obj).getId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "KycBVNVerification{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", bvn='" + bvn + '\'' +
                '}';
    }
}
