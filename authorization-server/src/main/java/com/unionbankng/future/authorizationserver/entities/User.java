package com.unionbankng.future.authorizationserver.entities;

import com.unionbankng.future.authorizationserver.enums.UserType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
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
    @NotNull
    @Column(length=32, nullable = false)
    private String firstName;
    @Column(length=32, nullable = false)
    private String lastName;
    @Column(length=50, nullable = false, unique = true)
    private String uuid;
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
    @Column(nullable = false)
    private Boolean isEnabled = true;
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public User(User user){

        id = user.getId();
    }

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
