package com.unionbankng.future.authorizationserver.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConfirmationToken implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String token;
    @OneToOne()
    @JoinColumn(nullable = false)
    private User user;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    public UserConfirmationToken(User user, int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        this.expiryDate = cal.getTime();
        this.user = user;
        this.token = UUID.randomUUID().toString();
    }
}
