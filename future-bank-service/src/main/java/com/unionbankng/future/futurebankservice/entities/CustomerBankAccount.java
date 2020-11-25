package com.unionbankng.future.futurebankservice.entities;

import com.unionbankng.future.futurebankservice.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String accountNumber;
    private Long customerUBNId;
    private String branchCode;
    private String accountType;
    @Column(nullable = false)
    private String userUUID;
    @Enumerated
    private AccountStatus accountStatus;
    @Column(nullable = false)
    private String accountName;
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
    public boolean equals(Object customerBankAccount) {
        return this.id.equals(((CustomerBankAccount)customerBankAccount).getId());

    }
}
