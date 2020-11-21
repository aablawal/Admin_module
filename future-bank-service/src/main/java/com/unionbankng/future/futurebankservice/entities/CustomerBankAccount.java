package com.unionbankng.future.futurebankservice.entities;

import com.unionbankng.future.futurebankservice.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

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
    private String accountNumber;
    private Long customerId;
    @Column(nullable = false)
    private String userUUID;
    @Enumerated
    private AccountStatus accountStatus;
}
