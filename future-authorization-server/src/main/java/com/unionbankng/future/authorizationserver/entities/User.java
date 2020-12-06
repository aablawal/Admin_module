package com.unionbankng.future.authorizationserver.entities;

import com.unionbankng.future.authorizationserver.enums.AuthProvider;
import com.unionbankng.future.authorizationserver.enums.ProfileType;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private String coverImg;
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
    private String password;
    @Column(length=5)
    private String dialingCode;
    @Column(length=32, unique = true)
    private String phoneNumber;
    @Column(length = 12)
    private String accountNumber;
    private String accountName;
    private String userAddress;
    @Column(length = 3)
    private String country;
    @Column(length = 50)
    private String stateOfResidence;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Column(nullable = false)
    private Boolean isEnabled = true;
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    public User(User user){
        this.isEnabled = user.getIsEnabled();
        this.id = user.getId();
        this.img = user.getImg();
        this.coverImg = user.getCoverImg();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.uuid = user.getUuid();
        this.authProvider = user.getAuthProvider();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    @PrePersist
    private void setCreatedAt() {
        createdAt = new Date();
    }

    @PreUpdate
    private void setUpdatedAt() {
        dateUpdated = new Date();
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
