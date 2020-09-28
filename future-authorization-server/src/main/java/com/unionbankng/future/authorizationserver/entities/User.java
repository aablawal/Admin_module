package com.unionbankng.future.authorizationserver.entities;

import com.unionbankng.future.authorizationserver.enums.UserType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String img;
    @NotNull
    @Column(length=32, nullable = false)
    private String firstName;
    @Column(length=32, nullable = false)
    private String lastName;
    @Column(length=50, nullable = false, unique = true)
    private String uuid;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @NotNull
    @Column(length=5, nullable = false)
    private String dialingCode;
    @Column(length=32, nullable = false)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(length = 12)
    private String accountNumber;
    private String address;
    @Column(length = 3)
    private String country;
    @Column(length = 50)
    private String stateOfResidence;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Column(nullable = false)
    private Boolean isEnabled = true;
    private Boolean isEmployer = false;
    private Boolean isFreelancer = false;
    @Column(precision = 19, scale = 2)
    private BigDecimal pricePerHour;
    private String jobTitle;
    @Column(columnDefinition="TEXT")
    private  String bio;
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
    public boolean equals(Object user) {
        return this.id.equals(((User)user).getId());

    }

    @Override
    public String toString() {
        return this.firstName+" "+this.lastName;
    }
}
